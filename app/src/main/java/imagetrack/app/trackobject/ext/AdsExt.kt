package imagetrack.app.trackobject.ext

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdRequest
import imagetrack.app.trackobject.ads.NativeAds
import imagetrack.app.trackobject.ads.NativeProgressAds


 fun TemplateView.ads(lifecycleScope: LifecycleCoroutineScope, context: Context, unitId: String, textView: TextView){

    val loaded=   NativeAds.intializeNativeAds( lifecycleScope,context, unitId
        , this, textView
    ){

    }

    println("Thread "+Thread.currentThread().name)
//    loaded.loadAd(AdManagerAdRequest.Builder().build())
    loaded.loadAd(AdRequest.Builder().build())
}


 fun TemplateView.ads(lifecycleScope: LifecycleCoroutineScope,context: Context, unitId: String, textView: ImageView){

    val loaded=   NativeProgressAds.intializeNativeAds( lifecycleScope,context, unitId
        , this, textView
    ){

    }

    println("Thread "+Thread.currentThread().name)
//    loaded.loadAd(AdManagerAdRequest.Builder().build())
    loaded.loadAds(AdRequest.Builder().build(), 1)
}