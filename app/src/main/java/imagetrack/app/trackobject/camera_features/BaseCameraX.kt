package imagetrack.app.trackobject.camera_features

import android.view.ScaleGestureDetector
import android.view.Surface
import androidx.camera.core.*
//import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.ScanFragmentDataBinding
import kotlinx.coroutines.Runnable

//@ExperimentalUseCaseGroupLifecycle
@ExperimentalGetImage
@ExperimentalUseCaseGroup
abstract class BaseCameraX(private val  cameraMetaData: CameraMetaData) :
                             ICamera ,ILifeCycleBinder {


    private var camera: Camera? = null
    private var cameraProvider : ProcessCameraProvider?=null
    private var mScanFragmentDataBinding : ScanFragmentDataBinding?=null

    override fun getCamera(): Camera? =camera
    override fun getCameraInfo() :CameraInfo?= camera?.cameraInfo
    override  fun getCameraControl() :CameraControl? = camera?.cameraControl
    override fun getTorchState(): LiveData<Int>? =camera?.cameraInfo?.torchState
    override fun getZoomState(): LiveData<ZoomState>? =camera?.cameraInfo?.zoomState

    companion object {
        private var mCameraSelector =CameraSelector.DEFAULT_BACK_CAMERA }

    init {
        val viewBindingLocal =    cameraMetaData.getViewDataBinding()

        if(viewBindingLocal is ScanFragmentDataBinding){
            mScanFragmentDataBinding =    viewBindingLocal }
//
////        try {
////            cameraProvider = ProcessCameraProvider.getInstance(cameraMetaData.getContext()).get()
////        }catch (e :Exception){
////
////           throw  Exception("Something Wrong "+e.message)
////
////        }

    }




    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
   override fun onCameraDestroy(){
        println("OnDestroy")
        cameraMetaData.getContext().resources.getDrawable(R.drawable.ic_flash_off,null )
        stop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){

//        startCamera()
    }


    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        val delta = detector?.scaleFactor ?: return false
        setZoomRatio(delta)
            return true }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {

        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @ExperimentalGetImage
   override fun onCameraResume(){
        println("ON_RESUME")

    //    startCamera()
    }


   abstract fun stop()

    override fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider
            .getInstance(cameraMetaData.getContext())
        cameraProviderFuture.addListener(Runnable {
            println("Start Camera")

            cameraProvider =  cameraProviderFuture.get()
            bindAllCameraXUseCases()

        },ContextCompat.getMainExecutor(cameraMetaData.getContext()))




    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onCameraPause(){
     println("OnPause")
    }


  //  override fun bindAllCameraXUseCases() {

//            unbindAll()
    //    providePreviewUseCase()

      //  }


     fun unbindAll(){
        val mCameraProvider = cameraProvider
        if(mCameraProvider!=null) {
            try {

                println("unbindall")
                mCameraProvider.unbindAll()
            } catch (e :IllegalStateException ){

                throw IllegalStateException("Unbind all exceptions")

             //   println("UnBindAll Illegal State Exception ")

            }


        } }


    //Method Modularization
     fun providePreviewUseCase():Preview{

        mScanFragmentDataBinding?.include2?.previewFinder?.display?.rotation.run {

            return Preview.Builder().also {

                println("Preview usecase")
            }

                .setTargetRotation(this?:Surface.ROTATION_90)
                .setTargetAspectRatio(AspectRatio.RATIO_4_3).build()
        }

    }

    private fun unbind(useCase: UseCase){

     val mCameraProvider =   cameraProvider
        try {

            println("unbind")

            if(mCameraProvider!=null) {
                cameraProvider?.unbind(useCase)
            }else{

                throw NullPointerException("unBind Camera Provider must not be null")

            }

        }catch (e : IllegalStateException ){
            println("Exception Occured in Thread Main ")
        }

    }




    override fun bindLifecycleView( previewUseCase:  UseCase ,useCase2: UseCase){
        val cameraProvider =cameraProvider

        if(cameraProvider!=null) {
            camera = cameraProvider.bindToLifecycle(
                cameraMetaData.getLifecycleOwner(),
                mCameraSelector,
                previewUseCase,useCase2)

        }else{
            throw NullPointerException("Camera Provider must not be null")
        }
    }

    override fun setZoomRatio(delta: Float) {
        val currentZoomRatio = camera?.cameraInfo?.zoomState?.value?.zoomRatio ?: 0F

        camera?.cameraControl?.setZoomRatio(currentZoomRatio * delta)

    }


    override fun startFocusAndMetering(point: MeteringPoint) {

        val action = FocusMeteringAction.Builder(point).build()
        camera?.cameraControl?.startFocusAndMetering(action)
    }

  private   fun torchOn(){
      camera?.cameraControl?.enableTorch(true)
    }

    private fun torchOff(){
        camera?.cameraControl?.enableTorch(false)
    }

    override fun enableTorch() {
        when(camera?.cameraInfo?.torchState?.value) {

         0->torchOn()
         1-> torchOff()
            else-> torchOff()
        }
    }

   abstract fun provideSurface(preview : Preview)




}