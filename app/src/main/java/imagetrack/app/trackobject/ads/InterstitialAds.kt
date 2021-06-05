package imagetrack.app.trackobject.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object InterstitialAds {
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "InterstitialAds"
    private const val TEST_UNIT_ID="ca-app-pub-3940256099942544/1033173712"


    fun load(context: Context){


        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context,TEST_UNIT_ID, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
            val message =   adError.message

                println("onError   $message")

               // Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                println( "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

    }

    fun show(activity  : Activity){
        mInterstitialAd?.show(activity)

    }



}