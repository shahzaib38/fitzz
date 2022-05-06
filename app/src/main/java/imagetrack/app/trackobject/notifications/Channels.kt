package imagetrack.app.trackobject.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

object Channels {

    const val UPDATE_CHANNEL_ID ="update_app"
    const val UPDATE_CHANNEL_NAME ="update app"
    const val UPDATE_OFFER_ID ="offer_id"
    const val UPDATE_OFFER_NAME ="Offers"


    fun createUpdateNotification(): NotificationChannel {

        val mChannels =NotificationChannel(
            UPDATE_CHANNEL_ID,
            UPDATE_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT)

        mChannels.description ="App Update Notifications"
         return mChannels }


    fun createOfferNotification(): NotificationChannel {
        return NotificationChannel(
            UPDATE_OFFER_ID,
            UPDATE_OFFER_NAME,
            NotificationManager.IMPORTANCE_DEFAULT) }


}

