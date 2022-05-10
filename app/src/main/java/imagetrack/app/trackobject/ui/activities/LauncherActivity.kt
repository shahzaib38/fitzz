package imagetrack.app.trackobject.ui.activities


import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.databinding.LauncherDataBinding
import imagetrack.app.trackobject.viewmodel.LauncherViewModel
import android.view.*



@AndroidEntryPoint
class LauncherActivity : BaseActivity<LauncherViewModel, LauncherDataBinding>() {

    private val mViewModel by viewModels<LauncherViewModel>()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = imagetrack.app.trackobject.R.layout.launcher_activity
    override fun getViewModel(): LauncherViewModel = mViewModel

    private var mLauncherDataBinding : LauncherDataBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mLauncherDataBinding = getViewDataBinding()

    }


    // Called when leaving the activity
    public override fun onPause() {
        super.onPause() }

    // Called when returning to the activity
    public override fun onResume() {
        super.onResume() }

    // Called before the activity is destroyed
    public override fun onDestroy() {
        super.onDestroy() }






}
