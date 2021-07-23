package imagetrack.app.trackobject.database.local.inappdatabase

import imagetrack.app.trackobject.database.local.SubscriptionStatus
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SubscriptionApi {



    @GET("v3/applications/{packageName}/purchases/subscriptions/{subscriptionId}/tokens/{token}")
    suspend fun getSubscriptionDetails(@Path("packageName") packageName : String ,
                                       @Path("subscriptionId") subscriptionId : String,
                                       @Path("token")  token :String,
                                       @Query("access_token") access_token  :String): SubscriptionStatus



}