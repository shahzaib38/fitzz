package imagetrack.app.trackobject.ads

import android.content.Context
import android.view.View
import com.google.android.gms.ads.*

object BannerAds   :AdListener(){


    private val BANNER = AdSize.BANNER
    private val FULL_BANNER =AdSize.FULL_BANNER
    private val TEST = "ca-app-pub-3940256099942544/6300978111"


    private var adView :AdView?=null


    override fun onAdClicked() {

    }

    override fun onAdClosed() {

        adView?.visibility = View.GONE

    }

    override fun onAdFailedToLoad(loadError: LoadAdError) {

      val message=   loadError.message
        println(message)

    }

    override fun onAdLoaded() {

        adView?.visibility = View.VISIBLE
    }

    override fun onAdOpened() {


    }


    fun initialize(context : Context):BannerAds{
        val adView = AdView(context)

        adView.adSize = BANNER
        adView.adUnitId = TEST


        return this
    }

    fun load(adView: AdView){

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

    }



}