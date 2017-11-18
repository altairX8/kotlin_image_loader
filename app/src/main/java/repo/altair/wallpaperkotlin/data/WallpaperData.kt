package repo.altair.wallpaperkotlin.data

import com.google.gson.annotations.SerializedName

/**
 * Created by amitn on 18-11-2017.
 */

data class WallpaperData (@SerializedName("url") val url: String , @SerializedName("name") val name : String)