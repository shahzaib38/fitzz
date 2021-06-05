package imagetrack.app.trackobject.ads

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import imagetrack.app.trackobject.app.ShapeDetectorApplication

//
//class AppOpenManager {
//
//    private val LOG_TAG = "AppOpenManager"
//    private val AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294"
//    private val appOpenAd: AppOpenAd? = null
//
//    private val loadCallback: AppOpenAdLoadCallback? = null
//
//    private var shapeDetectorApplication: ShapeDetectorApplication? = null
//
//    /** Constructor  */
//    fun AppOpenManager(shapeDetectorApplication: ShapeDetectorApplication) {
//        this.shapeDetectorApplication = shapeDetectorApplication
//    }
//
//    /** Request an ad  */
//    fun fetchAd() {
//        // We will implement this below.
//    }
//
//    /** Creates and returns ad request.  */
//    private fun getAdRequest(): AdRequest? {
//        return Builder().build()
//    }
//
//    /** Utility method that checks if ad exists and can be shown.  */
//    fun isAdAvailable(): Boolean {
//        return appOpenAd != null
//    }
//
//
//}