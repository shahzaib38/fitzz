//package imagetrack.app.trackobject.services
//
//import android.app.*
//import android.content.ClipboardManager
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import android.os.IBinder
//import android.widget.RemoteViews
//import android.widget.Toast
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationCompat.PRIORITY_MIN
//import imagetrack.app.trackobject.R
//import imagetrack.app.trackobject.notifications.Channels
//import imagetrack.app.trackobject.notifications.Notifications
//import imagetrack.app.trackobject.ui.activities.InAppPurchaseActivity
//import android.app.NotificationManager
//
//import android.app.NotificationChannel
//import android.graphics.Color
//import androidx.annotation.RequiresApi
//import imagetrack.app.trackobject.app.ShapeDetectorApplication
//import imagetrack.app.translate.TranslateUtils
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.catch
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.launch
//
//
//class ScanService : Service() ,ClipboardManager.OnPrimaryClipChangedListener {
//
//    var coroutine = CoroutineScope(Dispatchers.IO)
//
//    private lateinit var  clipboardManager :ClipboardManager
//    private fun getLanguageApiData(item :String):MutableMap<String, String>{
//        val postParameters: MutableMap<String, String> = HashMap()
//        postParameters["q"] = item
//        postParameters["target"] ="hi"
//        postParameters["key"] = TranslateUtils.SCANNER_KEY
//        return postParameters
//    }
//    override fun onCreate() {
//        super.onCreate()
//
//     clipboardManager  =    applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//        clipboardManager.addPrimaryClipChangedListener(this)
//
//        val channelId =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                createNotificationChannel(Channels.UPDATE_CHANNEL_ID, "My Background Service")
//            } else {
//                // If earlier version channel ID is not used
//                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
//                ""
//            }
//
//        val notificationBuilder = NotificationCompat.Builder(this, channelId )
//        val notification = notificationBuilder
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setCategory(Notification.CATEGORY_SERVICE)
//            .build()
//
//
//        startForeground(1245 ,notification)
//
//    }
//
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel(channelId: String, channelName: String): String{
//        val chan = NotificationChannel(channelId,
//            channelName, NotificationManager.IMPORTANCE_HIGH)
//
//        chan.lightColor = Color.BLUE
//        val service =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        service.createNotificationChannel(chan)
//
//        return channelId
//    }
//
//
//    override fun onPrimaryClipChanged() {
////
////     val data =    getLanguageApiData(clipboardManager.text.toString())
////
////       val shape = (applicationContext) as ShapeDetectorApplication
////        coroutine.launch {
////            shape.mainRepository.getUsersFlow(data)
////                .catch { exception ->
////
////                    val message = exception.message
////                    println("Error$message")
////                    Toast.makeText(applicationContext ,message.toString(),Toast.LENGTH_LONG).show()
////
////                }.collect {
////                    val data = it.getData()
////                    if (data != null) {
////                        val translation = data.getTranslations()
////                        translation?.forEach { translations ->
////                            if (translations != null) {
////                                val text = translations.getTranslatedText()
////                                Toast.makeText(applicationContext ,text.toString(),Toast.LENGTH_LONG).show()
////
////                                println("Translated " + text)
////
////                            }
////                        }
////                    }
////
////                }
////        }
//    }
//
//    override fun onDestroy() {
//
//        println("nDestory Intent Service")
//        super.onDestroy()
//
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//}