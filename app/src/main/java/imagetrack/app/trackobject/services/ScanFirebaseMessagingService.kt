package imagetrack.app.trackobject.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.notifications.Channels
import imagetrack.app.trackobject.notifications.Notifications

class ScanFirebaseMessagingService  : FirebaseMessagingService() {

    override fun onDestroy() {
        super.onDestroy()
        println("Scan Firebase MEssaging Sevice Destroyed")

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val notification =remoteMessage.notification
        if (notification != null) {
           val mChannelId = notification.channelId
            if(mChannelId!=null){
                sendUpdateNotification(notification,mChannelId) }
        }
    }

    private fun sendUpdateNotification(notification: RemoteMessage.Notification,channelId :String ) {
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.data = Uri.parse(URL)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//
//
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val channelList = listOf(Channels.createUpdateNotification(),Channels.createOfferNotification())

//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentTitle(title)
//            .setContentText(messageBody)
//            .setContentIntent(pendingIntent)
//
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri).setStyle(
//                NotificationCompat.BigTextStyle()
//                .bigText(messageBody))
//            .addAction( R.mipmap.ic_launcher ,UPDATE,
//                pendingIntent)

     //   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
     //       val channel = NotificationChannel("test", title, NotificationManager.IMPORTANCE_DEFAULT)
      //      channel.description = messageBody


     val notificationBuilder =   when(notification.channelId){
         Channels.UPDATE_OFFER_ID ->{
                Notifications.createOfferNotification(this ,notification ,Channels.UPDATE_OFFER_ID) }
         else ->{
                Notifications.createUpdateNotification(this ,notification ,Channels.UPDATE_CHANNEL_ID) } }
        notificationManager.createNotificationChannels(channelList)

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }




    companion object{

        const val URL = "https://play.google.com/store/apps/details?id=imagetrack.app.trackobject"
        const val NOTIFICATION_ID = 111
        const val UPDATE ="Update"
    }




    override fun onNewToken(token : String) {

        if(token!=null){
            println("token$token")

        }


    }



}