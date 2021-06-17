package imagetrack.app.trackobject.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.MainDataBinding
import imagetrack.app.trackobject.ext.setupWithNavController
import imagetrack.app.trackobject.ui.dialogs.ExitDialogFragment
import imagetrack.app.trackobject.viewmodel.MainViewModel

@ExperimentalUseCaseGroup
@ExperimentalUseCaseGroupLifecycle
@AndroidEntryPoint
class MainActivity   : BaseActivity<MainViewModel, MainDataBinding>() {

   private val mViewModel by viewModels<MainViewModel>()
    private var currentNavController: LiveData<NavController>? = null
    private var mMainDataBinding : MainDataBinding?=null



    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_main
    override fun getViewModel(): MainViewModel = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mMainDataBinding = getViewDataBinding()
        setupBottomNavigationBar()


    }






    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }



    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navGraphIds = listOf(R.navigation.scan, R.navigation.live_nav)
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }

}