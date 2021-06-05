package imagetrack.app.trackobject.ext

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView


fun AdView.load(){
    val adRequest = AdRequest.Builder().build()
    this.loadAd(adRequest) }