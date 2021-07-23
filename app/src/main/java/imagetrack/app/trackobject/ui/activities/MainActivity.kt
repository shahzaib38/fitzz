package imagetrack.app.trackobject.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.databinding.MainDataBinding
import imagetrack.app.trackobject.ext.setupWithNavController
import imagetrack.app.trackobject.ui.dialogs.ExitDialogFragment
import imagetrack.app.trackobject.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

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


    public fun   getDate( milliSeconds :Long , dateFormat :String) :String
    {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat.getDateInstance().format(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val  calendar = Calendar.getInstance();
        calendar.timeInMillis = milliSeconds;
        return formatter.format(calendar.time);
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