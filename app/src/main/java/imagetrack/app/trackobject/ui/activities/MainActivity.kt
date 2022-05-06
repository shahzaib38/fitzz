package imagetrack.app.trackobject.ui.activities

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.RemoteViews
import androidx.activity.viewModels
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ClipBoardManager
import imagetrack.app.datastore.IDataStore
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.MainDataBinding
import imagetrack.app.trackobject.ext.setupWithNavController
import imagetrack.app.trackobject.ext.showInstructionDialog
import imagetrack.app.trackobject.notifications.Channels
import imagetrack.app.trackobject.services.ScanFirebaseMessagingService
import imagetrack.app.trackobject.services.ScanFirebaseMessagingService.Companion.NOTIFICATION_ID
import imagetrack.app.trackobject.services.ScanService
import imagetrack.app.trackobject.ui.dialogs.ProgressDialogFragment
import imagetrack.app.trackobject.ui.dialogs.SpeakingDialogFragment
import imagetrack.app.trackobject.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

@ExperimentalUseCaseGroup
@AndroidEntryPoint
class MainActivity   : BaseActivity<MainViewModel, MainDataBinding>() , FragmentManager.OnBackStackChangedListener {



    override fun onBackStackChanged() {
       val count = this.supportFragmentManager.backStackEntryCount
        Log.i("count" , "${count} ")
        for(i :Int in 0 until count step 1){
       val entry =this.supportFragmentManager?.getBackStackEntryAt(i)
            Log.i("count entry" , "${entry.name } ") }
        println("BackStack Changed ")


    }

    private val mViewModel by viewModels<MainViewModel>()
    private var currentNavController: LiveData<NavController>? = null
    private var mMainDataBinding : MainDataBinding?=null
   // private var progressDialog : ProgressDialogFragment?=null

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_main
    override fun getViewModel(): MainViewModel = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mMainDataBinding = getViewDataBinding()


        // Remaining code

    }

    override fun onDestroy(){
        super.onDestroy()
    }



}






















//

//       val isNew = IDataStore.getInstance(applicationContext).getWelcomeNote()
//        if(!isNew) {
//            this.showInstructionDialog() }
//
//        this.supportFragmentManager.addOnBackStackChangedListener(this)
//
////       startService(Intent(this ,ScanService::class.java))
//        val navHostFragment = supportFragmentManager.findFragmentById(
//            R.id.nav_host_container
//        ) as NavHostFragment
//
//        val  navController = navHostFragment.navController
//

//        // Setup the bottom navigation view with navController
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
//
//        bottomNavigationView.setupWithNavController(navController)