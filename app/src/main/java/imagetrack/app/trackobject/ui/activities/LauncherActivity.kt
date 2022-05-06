package imagetrack.app.trackobject.ui.activities

//import com.google.android.gms.ads.admanager.AdManagerAdRequest
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.database.preferences.AdThreshold
import imagetrack.app.trackobject.databinding.LauncherDataBinding
import imagetrack.app.trackobject.demo.RemoteCoroutine
import imagetrack.app.trackobject.ext.launchActivity
import imagetrack.app.trackobject.notifications.Notifications
import imagetrack.app.trackobject.services.ScanFirebaseMessagingService
import imagetrack.app.trackobject.viewmodel.LauncherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.R
import android.app.Notification

import android.app.NotificationChannel
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import android.view.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import imagetrack.app.trackobject.navgraph.NavGraph
import imagetrack.app.trackobject.services.JobSchedularIntent
import imagetrack.app.trackobject.services.TranslateAccessibilitService


@AndroidEntryPoint
class LauncherActivity : BaseActivity<LauncherViewModel, LauncherDataBinding>() ,View.OnTouchListener {

    private val mViewModel by viewModels<LauncherViewModel>()

    private lateinit var gestureDetector : GestureDetector
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = imagetrack.app.trackobject.R.layout.launcher_activity

    override fun getViewModel(): LauncherViewModel = mViewModel

    private var mLauncherDataBinding : LauncherDataBinding?=null

    companion object{

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                          WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mLauncherDataBinding = getViewDataBinding()




    }




//private fun  setupAds(){
//
//    mLauncherDataBinding?.adsInclude?.apply {
//        val adRequest = AdRequest.Builder().build()
//        this.loadAd(adRequest)
//        this.adListener =object : AdListener(){
//
//            override fun onAdClicked() {
//                super.onAdClicked()
//                lifecycleScope.launch(Dispatchers.IO){
//                    AdThreshold.getInstance(this@LauncherActivity).save(1) }
//            }
//
//        }
//
//
//    }
//
//}

    // Called when leaving the activity
    public override fun onPause() {
//        mLauncherDataBinding?.adsInclude?.apply {
//            this.pause()
//            println("Pause" + this)
//
//        }
        super.onPause()
    }

    // Called when returning to the activity
    public override fun onResume() {
        super.onResume()
//        mLauncherDataBinding?.adsInclude?.apply {
//            this.resume()

            println("Resume" + this)
        }


//        LocalBroadcastManager.getInstance(this).registerReceiver(object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent?) {
//
//                Toast.makeText(this@LauncherActivity,"Test" ,
//                    Toast.LENGTH_LONG).show()
//
//            }
//
//
//        } , IntentFilter(JobSchedularIntent.TEST_BROADCAST))
//

    //}

    // Called before the activity is destroyed
    public override fun onDestroy() {
//        mLauncherDataBinding?.adsInclude?.apply {
//            this.destroy()
//            println("onDestroy")
//
//        }

        super.onDestroy()
    }

private fun testNotification(){


    try {
        println("Notification test")
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifyID = 154
        val CHANNEL_ID = "my_channel_01" // The id of the channel.

        val name: String = "test_channel" // The user-visible name of the channel.

        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
// Create a notification and set the notification channel.
// Create a notification and set the notification channel.
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("New Message")
            .setContentText("You've received new messages.")
            .setSmallIcon(imagetrack.app.trackobject.R.drawable.notifiationbitmap)
            .setChannelId(CHANNEL_ID)
            .build()
        notificationManager.notify(notifyID, notification)


    }catch(e : Exception ){

        println("Exception " +e.message)
    }

    }



    private fun startWorker() {
        startService(Intent(this ,
            TranslateAccessibilitService::class.java))

//        var JOB_ID =1212
//        var   jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE)as JobScheduler
//        val componentName = ComponentName(this, JobSchedularIntent::class.java)
//        var    builder = JobInfo.Builder(JOB_ID, componentName)
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_CELLULAR)
//        jobScheduler.schedule(builder.build())
    }

//    override fun onTouch(v: View?, event: MotionEvent?): Boolean
//    { gestureDetector.onTouchEvent(event)
//
//        return true }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        return super.onTouchEvent(event)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev) }





}
