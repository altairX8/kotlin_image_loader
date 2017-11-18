package repo.altair.wallpaperkotlin.Adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.squareup.picasso.Picasso
import repo.altair.wallpaperkotlin.R
import repo.altair.wallpaperkotlin.Util.Constants
import repo.altair.wallpaperkotlin.data.WallpaperData
import repo.altair.wallpaperkotlin.data.WallpaperList
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequest
import kotlinx.android.synthetic.main.wallpaper_item.view.*


/**
 * Created by amitn on 18-11-2017.
 */

class WallpaperAdapter (public var wallpaperList: WallpaperList?, public var libraryType: String) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder> (){


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent?.context).inflate(R.layout.wallpaper_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (wallpaperList != null) {
            holder?.bindViews(wallpaperList!!.wallpapers[position], libraryType)
        }
    }

    override fun getItemCount(): Int {
        if (wallpaperList != null) {
            return wallpaperList!!.wallpapers.size
        }
        return 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindViews(item: WallpaperData, type : String){

            itemView.tx_wallpaper_item_name.text = item.name
            if(type.equals(Constants.LIBRARY_TYPE_FRESCO,true)){
                itemView.img_wallpaper_item_fresco.visibility = View.VISIBLE
                itemView.img_wallpaper_item.visibility = View.GONE
                val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(item.url))
                        .setResizeOptions(ResizeOptions(600, 600))
                        .build()
                val controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(  itemView.img_wallpaper_item_fresco.controller)
                        .setImageRequest(request)
                        .build()

                itemView.img_wallpaper_item_fresco.controller = controller
            }else if(type.equals(Constants.LIBRARY_TYPE_FRESCO,true)){
                itemView.img_wallpaper_item_fresco.visibility = View.GONE
                itemView.img_wallpaper_item.visibility = View.VISIBLE
                Glide
                        .with(itemView.context).asBitmap().load(Uri.parse(item.url))
                        .into( itemView.img_wallpaper_item)
            }else {
                itemView.img_wallpaper_item_fresco.visibility = View.GONE
                itemView.img_wallpaper_item.visibility = View.VISIBLE
                Picasso.with(itemView.context).load(item.url).resize(500,500).into( itemView.img_wallpaper_item)
            }
        }
    }
}