package imagetrack.app.trackobject.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.preferences.AdThreshold
import imagetrack.app.trackobject.databinding.MainDataBinding
import imagetrack.app.trackobject.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalUseCaseGroup
@AndroidEntryPoint
class MainActivity   : BaseActivity<MainViewModel, MainDataBinding>() , FragmentManager.OnBackStackChangedListener {



    override fun onBackStackChanged() {}

    private val mViewModel by viewModels<MainViewModel>()
    private var mMainDataBinding : MainDataBinding?=null

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_main
    override fun getViewModel(): MainViewModel = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mMainDataBinding = getViewDataBinding()

       //       setupAds()

    }


//    private fun  setupAds(){
//        mMainDataBinding?.adViewId?.bannerId?.apply {
//            val adRequest = AdRequest.Builder().build()
//            this.loadAd(adRequest)
//            this.adListener =object : AdListener(){
//
//                override fun onAdClicked() {
//                    super.onAdClicked()
//
//                }
//
//            }
//        }
//    }



//
//
//
//    public override fun onPause() {
//        mMainDataBinding?.adViewId?.bannerId?.apply {
//            this.pause()
//            println("Pause" + this)
//
//        }
//        super.onPause()
//    }
//
//    public override fun onResume() {
//        super.onResume()
//        mMainDataBinding?.adViewId?.bannerId?.apply {
//            this.resume()
//
//            println("Resume" + this)
//        }
//
//    }
//
//
//    public override fun onDestroy() {
//        mMainDataBinding?.adViewId?.bannerId?.apply {
//            this.destroy()
//            println("onDestroy"+this)
//
//        }
//
//        super.onDestroy()
//    }

}










