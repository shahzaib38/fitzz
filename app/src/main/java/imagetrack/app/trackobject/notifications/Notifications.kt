package imagetrack.app.trackobject.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.notifications.Channels.UPDATE_CHANNEL_ID
import imagetrack.app.trackobject.services.ScanFirebaseMessagingService
import imagetrack.app.trackobject.ui.activities.InAppPurchaseActivity

object Notifications {
    const val URL = "https://play.google.com/store/apps/details?id=imagetrack.app.trackobject"



    fun createOfferNotification(context : Context ,notification: RemoteMessage.Notification ,channelId:String):NotificationCompat.Builder{
            val intent = Intent(context,InAppPurchaseActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)


        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction( R.mipmap.ic_launcher , ScanFirebaseMessagingService.UPDATE,
                pendingIntent)

        return notificationBuilder }

    fun createUpdateNotification(context : Context ,notification: RemoteMessage.Notification ,channelId:String):NotificationCompat.Builder{
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(URL)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction( R.mipmap.ic_launcher , ScanFirebaseMessagingService.UPDATE,
                pendingIntent)
        return notificationBuilder }

    fun createTranslatedNotification(context : Context , text :String):NotificationCompat.Builder{
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(URL)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val notificationBuilder = NotificationCompat.Builder(context, Channels.UPDATE_CHANNEL_ID)

        notificationBuilder.setOngoing(true).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Translated Text")
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setTicker(text)

        return notificationBuilder }



}