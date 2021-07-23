package imagetrack.app.trackobject.database.local.inappdatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object SubscriptionRetrofit {


    const val SUBSCRIPTION_BASE_URL="https://androidpublisher.googleapis.com/androidpublisher/"



    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient().newBuilder().writeTimeout(7, TimeUnit.SECONDS)
            .readTimeout(7, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS).build()
        return Retrofit.Builder().baseUrl(SUBSCRIPTION_BASE_URL).client(okHttpClient).addConverterFactory(
            GsonConverterFactory.create()).build()
    }


    @Singleton
    @Provides
    fun getSubscription(): SubscriptionApi {
        return getRetrofit().create(SubscriptionApi::class.java)
    }


}