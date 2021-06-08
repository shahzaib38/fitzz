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


    fun load(context: Context , unitId :String){


        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context,unitId, adRequest, object : InterstitialAdLoadCallback() {
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