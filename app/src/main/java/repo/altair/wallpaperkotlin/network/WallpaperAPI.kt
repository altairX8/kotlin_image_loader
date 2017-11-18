package repo.altair.wallpaperkotlin.network

import io.reactivex.Observable
import repo.altair.wallpaperkotlin.data.WallpaperList
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by amitn on 18-11-2017.
 */
interface WallpaperAPI  {

    @GET("/enter ur method here")
    fun getWallpaperList(): Observable<WallpaperList>

    companion object Factory {
        fun create() : WallpaperAPI{
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("enter you URL here")
                    .build()

            return retrofit.create(WallpaperAPI ::class.java)
        }
    }
}