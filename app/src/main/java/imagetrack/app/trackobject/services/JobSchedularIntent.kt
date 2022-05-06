package imagetrack.app.trackobject.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.RemoteViews
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.app.ShapeDetectorApplication
import imagetrack.app.trackobject.notifications.Channels
import imagetrack.app.translate.TranslateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class JobSchedularIntent : JobService() , View.OnTouchListener {


    companion object {
        const val TEST_BROADCAST ="test"
    }

     private   var  startViewX  =0
     private  var startViewY  =0
     private   var startTouchX  =0.0f
     private  var startTouchY = 0.0f
     private  var isMove :Int=0

     var coroutine = CoroutineScope(Dispatchers.IO)
     private lateinit var wParameters : WindowManager.LayoutParams
     private lateinit var view: View
     private lateinit var windowManager :WindowManager

    override fun onCreate() {
        super.onCreate()

       view= LayoutInflater.from(this).inflate(R.layout.floatingview_laout ,null )
        windowManager =getSystemService(WINDOW_SERVICE) as WindowManager
        wParameters = WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT ,WindowManager.LayoutParams.WRAP_CONTENT ,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT)
        wParameters.gravity =  Gravity.TOP
        wParameters.x =140
        wParameters.y =210
        view.setOnTouchListener(this)
        windowManager.addView(view ,wParameters)
    }


    private fun createNotificationEvent(){

        val remoteView = RemoteViews(this.packageName ,R.layout.translate_foreground_service_layout)

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(Channels.UPDATE_CHANNEL_ID, "My Background Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId )
        val notification = notificationBuilder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setCustomContentView(remoteView)
            .build()

        startForeground(1245 ,notification)

    }

    override fun onStartJob(params: JobParameters?): Boolean {
        println("OnHnadled work")

        createNotificationEvent()



        return true }


    override fun onStopJob(params: JobParameters?): Boolean {


        println("Job Stopped" +params)

        return true
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_HIGH)
        chan.lightColor = Color.BLUE
        val service =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)

        return channelId
    }

    private fun getLanguageApiData(item :String):MutableMap<String, String>{
        val postParameters: MutableMap<String, String> = HashMap()
        postParameters["q"] = item
        postParameters["target"] ="hi"
        postParameters["key"] = TranslateUtils.SCANNER_KEY
        return postParameters
    }


    override fun onDestroy() {
        super.onDestroy()



    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        var event = event ?: return false

        var action: Int = event.action

      return   when(action){

            MotionEvent.ACTION_DOWN ->{
                println("ACTION_DOWN")


                startViewX = wParameters.x
                startViewY = wParameters.y
                startTouchX = event.rawX
                startTouchY =event.rawY

                isMove =0

                true
            }

            MotionEvent.ACTION_UP ->{
                println("ACTION_UP")

                var EndTouchXPosition =event.rawX - startTouchX
                var EndTouchYPosition =event.rawX - startTouchY

                if(isMove<=5){
                    view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_LONG_CLICKED)

                }


                true
            }

            MotionEvent.ACTION_MOVE ->{
                println("ACTION_MOVE")

                wParameters.x   = startViewX +(event.rawX -startTouchX).toInt()
                wParameters.y   = startViewY +(event.rawY -startTouchY).toInt()

                windowManager.updateViewLayout(view ,wParameters)

                isMove++

                true
            }
          else -> {

              false
          }








        }


    }


}