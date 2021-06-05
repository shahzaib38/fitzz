package imagetrack.app.trackobject.camera_features

import android.content.Context
import android.view.ScaleGestureDetector
import androidx.camera.core.*
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import imagetrack.app.view.GraphicOverlay
import java.util.concurrent.Executors
import imagetrack.app.trackobject.R


@ExperimentalUseCaseGroupLifecycle
@ExperimentalGetImage
@ExperimentalUseCaseGroup
abstract class BaseCameraX( private val context: Context,
                            private val graphics: GraphicOverlay?,
                            private val lifecycleOwner: LifecycleOwner,
                            private val  previewView: PreviewView) :
                             ICamera,IPreview ,ILifeCycleBinder {


    private var cameraProvider: ProcessCameraProvider?=null
    private var camera: Camera? = null

    override fun getCamera(): Camera? =camera
    override fun getCameraInfo() :CameraInfo?= camera?.cameraInfo
    override  fun getCameraControl() :CameraControl? = camera?.cameraControl
    override fun getTorchState(): LiveData<Int> =camera?.cameraInfo?.torchState!!
    override fun getZoomState(): LiveData<ZoomState> =camera?.cameraInfo?.zoomState!!

    companion object {
        private var mCameraSelector =CameraSelector.DEFAULT_BACK_CAMERA }
    init {



            cameraProvider =  ProcessCameraProvider.getInstance(context).get()


    }



    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
   override fun onCameraDestroy(){
        println("OnDestroy")
        context.resources.getDrawable(R.drawable.ic_flash_off,null )

        stop() }


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
        bindAllCameraXUseCases()
    }


   abstract fun stop()

    override fun startCamera() {
        bindAllCameraXUseCases()
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onCameraPause(){
     println("OnPause")
        stop()
    }


  override  fun provideSurface(preview : Preview){
      preview.setSurfaceProvider(previewView.surfaceProvider) }

    override fun bindAllCameraXUseCases() {
        if(cameraProvider!=null) {
            cameraProvider!!.unbindAll()
            providePreviewUseCase() } }


    //Method Modularization
   override  fun providePreviewUseCase(){
            val previewUseCase= Preview.Builder()
            .setBackgroundExecutor(Executors.newSingleThreadExecutor())
            .setTargetAspectRatio(AspectRatio.RATIO_4_3).build()
        cameraProvider?.unbind(previewUseCase)
        provideSurface(previewUseCase)
        bindLifecycleView(previewUseCase)

    }


    override fun bindLifecycleView( useCase: UseCase){
    camera=   cameraProvider?.bindToLifecycle(lifecycleOwner, mCameraSelector , useCase )

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
         0 ->{   torchOn()
         }
         1->{ torchOff()
        }
            else->{
                torchOff()
            }
        }

    }

}