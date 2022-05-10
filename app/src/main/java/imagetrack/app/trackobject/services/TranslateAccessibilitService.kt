//package imagetrack.app.trackobject.services
//
//import android.accessibilityservice.AccessibilityService
//import android.accessibilityservice.AccessibilityServiceInfo
//import android.app.Notification
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.ClipboardManager
//import android.content.Context
//import android.graphics.Color
//import android.graphics.PixelFormat
//import android.graphics.Rect
//import android.os.Build
//import android.view.*
//import android.view.accessibility.AccessibilityEvent
//import android.view.accessibility.AccessibilityEvent.*
//import imagetrack.app.trackobject.R
//import imagetrack.app.trackobject.app.ShapeDetectorApplication
//import imagetrack.app.translate.TranslateUtils
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import android.view.accessibility.AccessibilityNodeInfo
//import android.widget.*
//import androidx.annotation.RequiresApi
//import androidx.core.app.NotificationCompat
//import imagetrack.app.trackobject.notifications.Channels
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import android.widget.ArrayAdapter
//import androidx.appcompat.widget.AppCompatSpinner
//import imagetrack.app.utils.InternetConnection
//import android.content.ClipData
//import androidx.constraintlayout.widget.ConstraintLayout
//import imagetrack.app.lanuguages.LanguageSupportModel
//import imagetrack.app.utils.LanguageArray
//
//
//class TranslateAccessibilitService : AccessibilityService() , View.OnTouchListener
//    , ClipboardManager.OnPrimaryClipChangedListener , View.OnClickListener {
//
//
//
//    /** Content View Child Views **/
//    private lateinit var contentView :View
//    private lateinit var copyToClipBoard: ImageView
//    private lateinit var copyToTranslateTextView: TextView
//    private lateinit var floatingProgress: ProgressBar
//    private lateinit var changeWindowScreen: ImageView
//    private lateinit var mainConstraintlayout: ConstraintLayout
//    private lateinit  var appCompatSpinner: AppCompatSpinner
//
//
//    private var editTextArray = HashSet<AccessibilityNodeInfo>()
//
//
//    /**Coroutine Scope **/
//    private var coroutine = CoroutineScope(Dispatchers.IO)
//
//    /*** Floating View Child **/
//    private lateinit var floatingView: View
//
//
//    /** Window manager Contents **/
//    private lateinit var windowManager :WindowManager
//    private lateinit var wParameters : WindowManager.LayoutParams
//
//    /** TOuch Listener ***/
//    private var  isMove :Int = 0
//    private var  startViewX  =0
//    private var startViewY  =0
//    private var startTouchX  =0.0f
//    private var startTouchY = 0.0f
//
//
//    /*** ClipBoard **/
//   private  var clipboard: ClipboardManager?=null
//
//
//    companion object{
//
//        private const val VIEW_RESOURCE_ID ="imagetrack.app.trackobject:id/floatingId"
//
//
//
//
//
//    }
//
//
//
//    override fun onCreate() {
//        super.onCreate()
//
//
//       clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
//        clipboard?.addPrimaryClipChangedListener(this)
//
//
//        createFloatingView()
//
//        createContentView()
//
//        createNotificationEvent()
//    }
//
//
//    private fun translateText(text :String){
//
//        coroutine.launch(Dispatchers.IO) {
//
//            val shape = (applicationContext) as ShapeDetectorApplication
//            val data =   getLanguageApiData(text)
//
//
//            try {
//
//                val userText = shape.mainRepository.getUsers(data)
//                val translate=     userText.getData()
//
//                if(translate!=null){
//                    translate.getTranslations()?.forEach{
//                        val translatedText =    it?.getTranslatedText()
//
//                        if(translatedText!=null){
//
//                            withContext(Dispatchers.Main) {
//                                val copyText = copyToTranslateTextView
//                                copyText.text = ""
//                                mainConstraintlayout.visibility = View.VISIBLE
//                                copyText.text = translatedText
//
//                            }
//                        }
//
//                    }
//
//
//                }
//
//            }catch (e : Exception){
//
//                withContext(Dispatchers.Main) {
//                    copyToTranslateTextView.text = ""
//                    copyToTranslateTextView.text = e.message
//
//                }
//                }
//
//
//
//
//        }
//
//
//
//
//    }
//
//
//    private fun createContentView(){
//
//            contentView= LayoutInflater.from(this).inflate(R.layout.notification_layout ,null )
//            windowManager =getSystemService(WINDOW_SERVICE) as WindowManager
//             wParameters = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT , WindowManager.LayoutParams.WRAP_CONTENT ,
//            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT)
//            wParameters.gravity =  Gravity.TOP
//
//            copyToTranslateTextView = contentView.findViewById(R.id.copToTranslate)
//            copyToClipBoard =contentView.findViewById(R.id.copyId)
//            floatingProgress = contentView.findViewById(R.id.floatingProgressId)
//            changeWindowScreen = contentView.findViewById(R.id.closeScreenId)
//            mainConstraintlayout =contentView.findViewById(R.id.mainConstraintId)
//            appCompatSpinner = contentView.findViewById(R.id.spinner2Id)
//
//
//            changeWindowScreen.setOnClickListener(this)
//            copyToClipBoard.setOnClickListener(this)
//
//
//        val dataAdapter: ArrayAdapter<LanguageSupportModel> = ArrayAdapter<LanguageSupportModel>(this, android.R.layout.simple_spinner_item, LanguageArray.arrayValues())
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        appCompatSpinner.adapter = dataAdapter
//
//        contentView.setOnTouchListener(this)
//        windowManager.addView(contentView ,wParameters)
//    }
//
//    private fun createFloatingView(){
//
//        floatingView= LayoutInflater.from(this).inflate(R.layout.floatingview_laout ,null )
//        windowManager =getSystemService(WINDOW_SERVICE) as WindowManager
//     var    wParameters = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT , WindowManager.LayoutParams.WRAP_CONTENT ,
//            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT)
////        wParameters.gravity =  Gravity.CENTER
//        wParameters.x =140
//        wParameters.y =410
//
//        floatingView.setOnTouchListener(this)
//        windowManager.addView(floatingView ,wParameters)
//    }
//
//    private fun getLanguageApiData(item :String):MutableMap<String, String>{
//
//          val languageSupportModel =   appCompatSpinner.selectedItem as LanguageSupportModel
//
//        val postParameters: MutableMap<String, String> = HashMap()
//        postParameters["q"] = item
//        postParameters["target"] = languageSupportModel.languageKey
//        postParameters["key"] = TranslateUtils.SCANNER_KEY
//        return postParameters }
//
//    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
//        println("Accessibility Service ")
//
//
//        if (!InternetConnection.isInternetAvailable(this)) {
//            val copyText = copyToTranslateTextView
//             copyText.gravity =Gravity.CENTER
//            copyText.text = ""
//            copyText.text =this.resources.getString(R.string.no_interet_connection)
//
//        }else{
//
//            processAction(event)
//        }
//
//    }
//
//
//
//    private fun processAction(event: AccessibilityEvent?) {
//
//        if(event ==null)return
//
//
//        when(event.eventType){
//
//            AccessibilityEvent.TYPE_VIEW_CLICKED ->{
//
//                println("source "+event.source!!.viewIdResourceName)
//            }
//
//
//            WINDOWS_CHANGE_ADDED ->{
//                println("WINDOWS_CHANGE_ADDED")
//
//                //              editTextArray.clear()
////                getEditText(this.rootInActiveWindow)
//
//
//            }
//
//
//
//
//            TYPE_VIEW_CLICKED ->{
//
//        println("CLICKED")
//
//
//                println("Source"+event.source.viewIdResourceName)
//
//
//                if(VIEW_RESOURCE_ID == event.source.viewIdResourceName) {
//
//
//                    println("CLICKED")
//
//                    val sourceRect =Rect()
//
//                    event.source?.getBoundsInScreen(sourceRect)
//
//                    editTextArray.clear()
//
//                    getEditText(this.rootInActiveWindow)
//
//                     editTextArray.forEach {
//
//                    val windowRect =Rect()
//                    it.getBoundsInScreen(windowRect)
//
//                    if(sourceRect.left >= windowRect.left && sourceRect.right <= windowRect.right
//                        && sourceRect.top >= windowRect.top && sourceRect.bottom <= windowRect.bottom)
//
//                    { translateText(it.text.toString()) }
//
//                } } }
//
//
//            //
//
//
//                    WINDOWS_CHANGE_REMOVED ->{
//                println("WINDOWS_CHANGE_REMOVED")
//
//
//            }
////
////                    WINDOWS_CHANGE_TITLE ->{
////
////                        println("WINDOWS_CHANGE_TITLE")
////
////                    }
////
////
////                    WINDOWS_CHANGE_BOUNDS ->{
////                        println("WINDOWS_CHANGE_BOUNDS")
////
////
////                    }
////
////
////                    WINDOWS_CHANGE_LAYER ->{
////                        println("WINDOWS_CHANGE_FOCUSED")
////
////                    }
////
////
////                    WINDOWS_CHANGE_ACTIVE ->{
////                        println("WINDOWS_CHANGE_ACTIVE")
////
////                    }
////
////                    WINDOWS_CHANGE_FOCUSED ->{
////                        println("WINDOWS_CHANGE_FOCUSED")
////
////
////                    }
////
////                    WINDOWS_CHANGE_ACCESSIBILITY_FOCUSED ->{
////                        println("WINDOWS_CHANGE_ACCESSIBILITY_FOCUSED")
////
////                    }
////
////                    WINDOWS_CHANGE_PARENT ->{
////                        println("WINDOWS_CHANGE_PARENT")
////
////                    }
////
////
////                    WINDOWS_CHANGE_CHILDREN ->{
////                        println("WINDOWS_CHANGE_CHILDREN")
////
////                    }
////
////                    WINDOWS_CHANGE_PIP->{
////
////                        println("WINDOWS_CHANGE_PIP")
////                    }
////
////            TYPE_TOUCH_INTERACTION_START ->{
////                println("TYPE_TOUCH_INTERACTION_START")
////
////
////            }
////            TYPE_TOUCH_INTERACTION_END ->{
////                println("TYPE_TOUCH_INTERACTION_END")
////
////
////            }
////
//
//
//        }
//
//    }
//
//    private fun getEditText(nodeInfo: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
//
//
//
//        if(nodeInfo ==null ) {
//            println("accessibibilty is null")
//            return null }
//
//        if(nodeInfo.className.toString().contains(EditText::class.java.simpleName) || nodeInfo.className.toString().contains(TextView::class.java.simpleName)){
//
//            editTextArray.add(nodeInfo)
//
//           }
//
//
//        for(i :Int in 0 until nodeInfo.childCount step 1){
//
//          val nodeInfo =  getEditText(nodeInfo.getChild(i))
//
//            if(nodeInfo !=null)return nodeInfo
//        }
//
//        return null
//    }
//
//    override fun onInterrupt() {
//        println("OnInterrupted")
//
//
//    }
//
//    override fun onServiceConnected() {
//    val info =    AccessibilityServiceInfo()
//        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS or AccessibilityServiceInfo.CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT or AccessibilityServiceInfo.CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION
//
//
//        info.apply {
//            eventTypes = WINDOWS_CHANGE_ADDED or
//            WINDOWS_CHANGE_REMOVED or
//            WINDOWS_CHANGE_TITLE or
//            WINDOWS_CHANGE_BOUNDS or
//            WINDOWS_CHANGE_LAYER or
//            WINDOWS_CHANGE_ACTIVE or
//            WINDOWS_CHANGE_FOCUSED or
//            WINDOWS_CHANGE_ACCESSIBILITY_FOCUSED or
//            WINDOWS_CHANGE_PARENT or
//            WINDOWS_CHANGE_CHILDREN or
//            WINDOWS_CHANGE_PIP or
//                    TYPE_VIEW_FOCUSED or
//                    TYPE_TOUCH_INTERACTION_START or
//                    TYPE_TOUCH_INTERACTION_END or
//                    TYPE_VIEW_CLICKED
//
//            feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK
//
//            notificationTimeout = 100
//        }
//        this.serviceInfo = info
//    }
//
//
//    private fun createNotificationEvent(){
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
//            .setContentTitle("Tap to  Translate")
//            .setContentText("Drag and Tap to translate an text")
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setCategory(Notification.CATEGORY_SERVICE)
//            .build()
//
//        startForeground(1245 ,notification)
//
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel(channelId: String, channelName: String): String{
//        val chan = NotificationChannel(channelId,
//            channelName, NotificationManager.IMPORTANCE_HIGH)
//        chan.lightColor = Color.BLUE
//        val service =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        service.createNotificationChannel(chan)
//
//        return channelId
//    }
//
//
//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        val event = event ?: return false
//
//        val action: Int = event.action
//
//        return   when(action){
//
//            MotionEvent.ACTION_DOWN ->{
//                println("ACTION_DOWN")
//
//
//                startViewX = wParameters.x
//                startViewY = wParameters.y
//                startTouchX = event.rawX
//                startTouchY =event.rawY
//
//                isMove =0
//
//                true
//            }
//
//            MotionEvent.ACTION_UP ->{
//                println("ACTION_UP")
//
//                var EndTouchXPosition =event.rawX - startTouchX
//                var EndTouchYPosition =event.rawX - startTouchY
//
//                if(isMove<=100){
//
//                       floatingView.performClick()
//                }
//
//                true }
//
//            MotionEvent.ACTION_MOVE ->{
//
//                wParameters.x   = startViewX +(event.rawX -startTouchX).toInt()
//                wParameters.y   = startViewY +(event.rawY -startTouchY).toInt()
//
//                windowManager.updateViewLayout(contentView ,wParameters)
//
//               isMove++
//
//                true }
//
//            else -> {
//                false }
//
//        } }
//
//    override fun onPrimaryClipChanged() {
//
//        Toast.makeText(this,"copied to ClipBoard",Toast.LENGTH_LONG).show()
//    }
//
//    override fun onClick(v: View?) {
//        if(v==null)return
//
//        when(v.id){
//
//            R.id.closeScreenId ->{
//                val mainCOnstraintLayout =  mainConstraintlayout
//                mainCOnstraintLayout.visibility =View.GONE }
//
//
//
//            R.id.copyId  ->{
//
//                val copyTOTranslate =copyToTranslateTextView
//                val text =    copyTOTranslate.text.toString()
//                val clip = ClipData.newPlainText("sb scanner" , text)
//                clipboard?.setPrimaryClip(clip)
//
//
//            }
//
//        }
//
//
//    }
//
//
//}
//
//
