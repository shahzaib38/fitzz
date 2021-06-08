package imagetrack.app.trackobject.ui

import android.os.Bundle
import androidx.activity.viewModels

import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.ads.NativAdAdvance
import imagetrack.app.trackobject.ads.OnAdVisibilityListener
import imagetrack.app.trackobject.databinding.DemoDataBinding
import imagetrack.app.trackobject.ui.activities.BaseActivity
import imagetrack.app.trackobject.viewmodel.MainViewModel


const val ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110"

@AndroidEntryPoint
class DemoActivity : BaseActivity<MainViewModel, DemoDataBinding>() ,OnAdVisibilityListener{

    private var mLiveFragmentDataBinding: DemoDataBinding? = null
    private val mViewModel by viewModels<MainViewModel>()
    override fun getBindingVariable(): Int  =BR.viewModel
    override fun getLayoutId(): Int  = imagetrack.app.trackobject.R.layout.demo
    override fun getViewModel(): MainViewModel = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLiveFragmentDataBinding = getViewDataBinding()

        mLiveFragmentDataBinding?.let{

            NativAdAdvance.loadAd(this , it ,this ,resources.getString(R.string.scan_editor_unitId))

        }

    }

    override fun showAd() {

    }

    override fun hideAd() {

    }
}
