package imagetrack.app.trackobject.camera_features

//
//interface  OpenDialog{
//
//    fun  openDialog(text :String)
//    fun closeDialog()
//
//
//}
//
//
//class ScanningCamera private constructor(
//    private val context: Context,
//    private val graphics: GraphicOverlay?,
//    lifecycleOwner: LifecycleOwner, previewView: PreviewView, val progress: ProgressBar
//) : BaseCameraX(
//    context, graphics,
//    lifecycleOwner, previewView
//) ,ICaptureUseCase ,OpenDialog{
//
//
//
//
//
//
//
//    var fragmentManager :  FragmentManager? = null
//
//  override  fun setFragmentManagerr(fragmentManager  : FragmentManager){
//
//        this.fragmentManager = fragmentManager }
//
//
//
//
//
//
//
//
//   override fun captureImage(){
//        imageCapture?.takePicture(
//            Executors.newSingleThreadExecutor(), ImageCaptureImpl.capture(
//                context,
//                this
//            )
//        )
//   }
//
//
//
//    fun openDialog(){
//
//
//
//
//    }
//
//
//
//
//    private var imageCapture : ImageCapture?=null
//    override fun stop() {
//
//        progress.visibility= View.GONE
//
//
//    }
//
//    @ExperimentalGetImage
//    @ExperimentalUseCaseGroup
//    @ExperimentalUseCaseGroupLifecycle
//    override fun bindAllCameraXUseCases() {
//        super.bindAllCameraXUseCases()
//        provideImageUseCase()
//    }
//
//    fun startProgress(){
//        Handler(Looper.getMainLooper()).post(Runnable {
//
//            println("thread Name ${Thread.currentThread().name}")
//
//        progress.visibility = View.VISIBLE
//
//        })
//    }
//
//
//    fun stopProgress(){
//        Handler(Looper.getMainLooper()).post(Runnable {
//            println("thread Name ${Thread.currentThread().name}")
//            progress.visibility=View.GONE
//        })
//
//    }
//
//
//    @ExperimentalGetImage
//    @ExperimentalUseCaseGroup
//    @ExperimentalUseCaseGroupLifecycle
//    override fun provideImageUseCase() {
//        imageCapture =
//            ImageCapture.Builder().setTargetAspectRatio(AspectRatio.RATIO_16_9).also {}
//                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
//                .build()
//
//        super.bindLifecycleView(imageCapture!!)
//    }
//
//    companion object{
//
//        @Volatile
//        private var instance  : LiveCameraX?=null
//
//        @ExperimentalGetImage
//        @ExperimentalUseCaseGroup
//        @ExperimentalUseCaseGroupLifecycle
//        fun provideCamera(
//            context: Context, graphics: GraphicOverlay?,
//            lifecycleOwner: LifecycleOwner,
//            previewView: PreviewView, progress: ProgressBar
//        ):ICamera{
//
//            if(instance ==null){
//                return ScanningCamera(context, graphics, lifecycleOwner, previewView, progress) }
//            return ScanningCamera(context, graphics, lifecycleOwner, previewView, progress) } }
//
//
//
//    override fun openDialog(text: String) {
//
//
//
//    }
//
//    override fun closeDialog() {
//
//    }
//
//}