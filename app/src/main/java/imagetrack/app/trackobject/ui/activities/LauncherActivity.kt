package imagetrack.app.trackobject.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.ads.nativetemplates.NativeTemplateStyle
//import com.google.android.gms.ads.admanager.AdManagerAdRequest
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
//import imagetrack.app.trackobject.ads.NativeAds
import imagetrack.app.trackobject.databinding.LauncherDataBinding
//import imagetrack.app.trackobject.ext.ads
import imagetrack.app.trackobject.ext.launchActivity
import imagetrack.app.trackobject.viewmodel.LauncherViewModel
import kotlinx.android.synthetic.main.ads_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : BaseActivity<LauncherViewModel, LauncherDataBinding>() {

    private val mViewModel by viewModels<LauncherViewModel>()

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.launcher_activity

    override fun getViewModel(): LauncherViewModel = mViewModel

    private var mLauncherDataBinding : LauncherDataBinding?=null

    companion object{

        const val VERSION_ID ="Version 3.6.1"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mLauncherDataBinding = getViewDataBinding()

        mLauncherDataBinding?.launcherId?.setOnClickListener {
            finish()
            this.launchActivity(MainActivity::class.java) }

        mLauncherDataBinding?.versionId?.text =VERSION_ID

     //   setupAds()
    }

//private fun  setupAds(){
//
//        mLauncherDataBinding?.adsInclude?.apply {
//
//            val unitId=    resources.getString(R.string.launcher_native)
//         //   adsId.ads(this@LauncherActivity ,unitId ,advertiseId)
//            lifecycleScope.launch(Dispatchers.IO) {
//                adsId.ads(this@LauncherActivity, unitId, advertiseId)
//            }
//        }
//
//    }


}