package imagetrack.app.trackobject.app

import android.app.Application
import androidx.databinding.library.BuildConfig
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp
import imagetrack.app.trackobject.repo.MainRepository
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ShapeDetectorApplication : Application() {


    @Inject
     lateinit var mainRepository :MainRepository


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return super.createStackElementTag(element) + ':' + element.lineNumber

                }
            }
            )


        }

        setupAdSdk()
    }

    private fun setupAdSdk(){

        MobileAds.initialize(this)
    }






}