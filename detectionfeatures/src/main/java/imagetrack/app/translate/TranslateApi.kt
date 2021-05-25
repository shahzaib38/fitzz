package imagetrack.app.translate

import imagetrack.app.lanuguages.TranslateDetect
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TranslateApi {


    @GET("v2")
    suspend  fun getData(@QueryMap name: Map<String, String>): TranslateDetect




}