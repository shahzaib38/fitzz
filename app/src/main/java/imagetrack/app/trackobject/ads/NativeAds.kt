package imagetrack.app.trackobject.ads

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdOptions.ADCHOICES_TOP_RIGHT
import imagetrack.app.trackobject.database.preferences.AdThreshold
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object NativeAds {


    fun intializeNativeAds(lifecycleScope: LifecycleCoroutineScope, context: Context, unitId: String, template: TemplateView, textView: TextView, function: suspend (native : NativeAd) -> Unit ): AdLoader{
        println("Thraed progress adsd"+Thread.currentThread().name)


        val videoOptions = VideoOptions.Builder()
            .setStartMuted(true)
            .build()

        val adOptions = NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .setRequestCustomMuteThisAd(true)
            .setAdChoicesPlacement(ADCHOICES_TOP_RIGHT)
            .build()

        val adLoaded = AdLoader.Builder(context, unitId).forNativeAd {
           val nativeAds  =  it



            val   mediaContent = nativeAds.mediaContent

            template.visibility =View.VISIBLE
            textView.visibility =View.GONE




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



                                template.setNativeAd(nativeAds)





        }.withNativeAdOptions(adOptions).withAdListener(object : AdListener(){
            override fun onAdFailedToLoad(loadError: LoadAdError) {
                println("Failure to Load adsss "+ loadError.code  +"message" +" "+loadError.message)

                println("thread 11 "+Thread.currentThread().name)
                template.visibility =View.GONE
                textView.visibility =View.VISIBLE


            }
        })



       return adLoaded.withAdListener(object : AdListener(){
           override fun onAdClicked() {
               super.onAdClicked()
               lifecycleScope.launch(Dispatchers.IO){
                       AdThreshold.getInstance(context).save(1) }



           }

       }).build()
    }





}