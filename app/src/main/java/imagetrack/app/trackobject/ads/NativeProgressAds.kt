package imagetrack.app.trackobject.ads

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object NativeProgressAds {


     fun intializeNativeAds(context: Context, unitId: String, template: TemplateView, textView: ImageView, function: suspend (native : NativeAd) -> Unit ): AdLoader {

         println("Thraed progress adsd"+Thread.currentThread().name)

        val videoOptions = VideoOptions.Builder()
            .setStartMuted(true)
            .build()

        val adOptions = NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .setRequestCustomMuteThisAd(true)
            .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
            .build()

        val adLoaded = AdLoader.Builder(context, unitId).forNativeAd {
            val nativeAds  =  it



            val   mediaContent = nativeAds.mediaContent

            template.visibility = View.VISIBLE
            textView.visibility = View.GONE




            if(mediaContent!=null  && mediaContent.hasVideoContent()){

                println("Vide is availble ")

                mediaContent.videoController.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks(){

                    override fun onVideoStart() {
                        super.onVideoStart()

                        println("Started ")
                    }

                    override fun onVideoPlay() {
                        super.onVideoPlay()
                        println("Play ")
                    }

                    override fun onVideoPause() {
                        super.onVideoPause()
                    }

                    override fun onVideoEnd() {
                        super.onVideoEnd()
                    }
                }


            }




            GlobalScope.launch(Dispatchers.Main) {


                template.setNativeAd(nativeAds)


            }


        }.withNativeAdOptions(adOptions).withAdListener(object : AdListener(){
            override fun onAdFailedToLoad(loadError: LoadAdError) {
                println("Failure to Load adsss "+ loadError.message )

                println("thread 11 "+Thread.currentThread().name)
                template.visibility = View.GONE
                textView.visibility = View.VISIBLE


            }
        })



        return adLoaded.build()
    }





}