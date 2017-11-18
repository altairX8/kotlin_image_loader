package repo.altair.wallpaperkotlin

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.facebook.drawee.backends.pipeline.Fresco
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_wallpaper.*
import repo.altair.wallpaperkotlin.Adapter.WallpaperAdapter
import repo.altair.wallpaperkotlin.Util.Constants
import repo.altair.wallpaperkotlin.data.WallpaperList
import repo.altair.wallpaperkotlin.network.WallpaperAPI

class WallpaperActivity : AppCompatActivity() {
    var wallListLocal: WallpaperList? = null
    var adapter : WallpaperAdapter? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                reloadWalls(Constants.LIBRARY_TYPE_PICASSO)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                reloadWalls(Constants.LIBRARY_TYPE_GLIDE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                reloadWalls(Constants.LIBRARY_TYPE_FRESCO)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)
        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(this)
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        loadWallPaperFromServer()

    }

    private fun loadWallPaperFromServer() {
        val wallpaperAPI = WallpaperAPI.create()

        wallpaperAPI.getWallpaperList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ t: WallpaperList? ->
                    if (t?.wallpapers != null && t.wallpapers.size > 0) {
                        wallListLocal = t
                        Log.d("Result", " json - " + t.wallpapers.size)
                        loadWalls(t)
                    }
                }, { error ->
                    error.printStackTrace()
                })
    }

    private fun loadWalls(wallList: WallpaperList) {
        wall_recycler.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        adapter = WallpaperAdapter(wallList, Constants.LIBRARY_TYPE_PICASSO)
        adapter?.setHasStableIds(true)

        wall_recycler.isDrawingCacheEnabled = true;
        wall_recycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH;
        wall_recycler.adapter = adapter
    }

    private fun reloadWalls(type: String) {
        if (wallListLocal != null && adapter != null) {
            adapter?.libraryType =type
            adapter?.notifyDataSetChanged()
        }
    }
}

