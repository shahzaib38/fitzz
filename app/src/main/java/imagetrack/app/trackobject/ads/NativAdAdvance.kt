package imagetrack.app.trackobject.ads

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdOptions.ADCHOICES_TOP_RIGHT
import com.google.android.gms.ads.nativead.NativeAdOptions.NATIVE_MEDIA_ASPECT_RATIO_SQUARE
import imagetrack.app.trackobject.databinding.DemoDataBinding
import imagetrack.app.trackobject.databinding.EditorDataBinding
import imagetrack.app.trackobject.databinding.LanguageListDataBinding
import imagetrack.app.trackobject.databinding.ScanDialogDataBinding
import imagetrack.app.trackobject.ui.TemplateView


object NativAdAdvance {

    private val TEST_AD = "ca-app-pub-3940256099942544/2247696110"

    private var vc: VideoController? = null
    fun loadAd(context: Activity, dataBinding: ViewDataBinding ,adOnAdVisibilityListener: OnAdVisibilityListener ,unitId :String) {
        val adLoader = AdLoader.Builder(context, unitId).forNativeAd { nativeAd ->

            processUi(nativeAd, dataBinding)

        }.withAdListener(object : AdListener() {

            override fun onAdFailedToLoad(loadError: LoadAdError) {
                println("Loading Error  ${loadError.message}")
            }

            override fun onAdClosed() {

                adOnAdVisibilityListener.hideAd()
            }

            override fun onAdLoaded() {
                println("Ad Loaded")
                vc?.play()
                adOnAdVisibilityListener.showAd()
            //    vc?.play()
            }

            override fun onAdClicked() {
                println("OnAd CLicked")
            }

        }).withNativeAdOptions(getVideoOption()).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }



    fun bindWithUi(it : TemplateView , nativeAd : NativeAd){

        val styles = NativeTemplateStyle.Builder().build()
            println("Yes binded")
            it.setStyles(styles)
            it.visibility = View.VISIBLE
            it.setNativeAd(nativeAd) }

    private fun processUi(nativeAd: NativeAd, dataBinding: ViewDataBinding) {
        when(dataBinding){
            is  LanguageListDataBinding ->{
                bindWithUi(dataBinding.template,nativeAd) }
            is ScanDialogDataBinding ->{
                bindWithUi(dataBinding.template,nativeAd)


            }
            is EditorDataBinding ->{
                bindWithUi(dataBinding.template,nativeAd)


            }

            is DemoDataBinding ->{
                bindWithUi(dataBinding.template,nativeAd)


            }

        }

        val mediaContent = nativeAd.mediaContent
        vc = mediaContent?.videoController
        // Updates the UI to say whether or not this ad has a video asset.
            controlVideo(vc) }



    private fun controlVideo(vc : VideoController?){
        if(vc==null ) return

        if (vc.hasVideoContent()) {
            vc.videoLifecycleCallbacks =object :VideoController.VideoLifecycleCallbacks(){

                override fun onVideoStart() {
                    super.onVideoStart()
                    println("Video Started")
                }

                override fun onVideoEnd() {
                    super.onVideoEnd()
                }

                override fun onVideoPause() {
                    super.onVideoPause()
                }

                override fun onVideoPlay() {
                    super.onVideoPlay()
                }


            }

        }


    }



    private   fun getVideoOption () :NativeAdOptions{

        val videoOptions = VideoOptions.Builder()
            .setStartMuted(true)
            .build()

        return  NativeAdOptions.Builder()
            .setVideoOptions(videoOptions).setMediaAspectRatio(NATIVE_MEDIA_ASPECT_RATIO_SQUARE)
            .setAdChoicesPlacement(ADCHOICES_TOP_RIGHT)
            .build() }






}