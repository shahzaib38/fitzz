package imagetrack.app.trackobject.ui

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.DemoDataBinding
import imagetrack.app.trackobject.ui.activities.BaseActivity
import imagetrack.app.trackobject.viewmodel.MainViewModel



@AndroidEntryPoint
class DemoActivity : BaseActivity<MainViewModel, DemoDataBinding>() {

    private var mLiveFragmentDataBinding: DemoDataBinding? = null
    private val mViewModel by viewModels<MainViewModel>()
    override fun getBindingVariable(): Int  =BR.viewModel
    override fun getLayoutId(): Int  = R.layout.demo
    override fun getViewModel(): MainViewModel = mViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLiveFragmentDataBinding = getViewDataBinding()


    }



    companion object {
        private const val TAG = "GoogleActivity"

    }





}
