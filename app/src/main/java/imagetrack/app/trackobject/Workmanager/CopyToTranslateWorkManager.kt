package imagetrack.app.trackobject.Workmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.notifications.Channels
import imagetrack.app.trackobject.notifications.Notifications
import java.util.*

class CopyToTranslateWorkManager(val appContext : Context, workerParameters: WorkerParameters) : CoroutineWorker(appContext ,workerParameters) {


    override suspend fun doWork(): Result {

        println("Worker Copy to Transalte")
        val foregroundInfo =getForeGroundInfo()
     setForeground(foregroundInfo)


       return  Result.success()
    }


   private  fun getForeGroundInfo(): ForegroundInfo{

       val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(Channels.UPDATE_CHANNEL_ID, "My Background Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

      //  val notificationBuilder = NotificationCompat.Builder(appContext, channelId )
//        val notification = notificationBuilder
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setCategory(Notification.CATEGORY_SERVICE)
//            .build()


       val notification = NotificationCompat.Builder(applicationContext, channelId)
           .setSmallIcon(R.mipmap.ic_launcher)
           .setOngoing(true)
           // Add the cancel action to the notification which can
           // be used to cancel the worker
           .build()

        return ForegroundInfo( 1245,notification) }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_HIGH)
        chan.lightColor = Color.BLUE
        val service =appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId }
}