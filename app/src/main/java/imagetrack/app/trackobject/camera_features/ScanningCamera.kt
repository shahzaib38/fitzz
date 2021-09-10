package imagetrack.app.trackobject.camera_features

import android.content.Context
import android.graphics.Bitmap
import android.hardware.display.DisplayManager
import androidx.camera.core.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import imagetrack.app.CallBack
import imagetrack.app.image_processing.DeviceTextRecognizer
import imagetrack.app.trackobject.databinding.ScanFragmentDataBinding
import java.util.concurrent.Executors


class ScanningCamera  constructor(private val cameraMetaData: CameraMetaData) :
    BaseCameraX(cameraMetaData) , ICaptureUseCase  ,CallBack  {

    private val progressStatus: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>()
    private val translateText :MediatorLiveData<String?> = MediatorLiveData<String?>()
    private val visionImageProcessor = DeviceTextRecognizer(
        cameraMetaData.getContext()
    )
    private var mScanFragmentDataBinding : ScanFragmentDataBinding?=null
    val executors = Executors.newSingleThreadExecutor()

   private  val displayManager by lazy {
        cameraMetaData.getContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }

    init {





        val viewBindingLocal =    cameraMetaData.getViewDataBinding()

        if(viewBindingLocal is ScanFragmentDataBinding){
            mScanFragmentDataBinding = viewBindingLocal
        }



        progressStatus.addSource(visionImageProcessor.getProgressLiveData()) {
            progressStatus.postValue(it) }


    }

    override fun captureImage(){

        imageCapture?.takePicture(Executors.newSingleThreadExecutor(),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    visionImageProcessor.processImageProxy(image)
                }

                override fun onError(exception: ImageCaptureException) {
                    progressStatus.postValue(false)

                }
            }
        )
    }



    private var imageCapture : ImageCapture?=null

    override fun stop() {

    translateText.postValue(null)
    }


    @ExperimentalGetImage
    @ExperimentalUseCaseGroup
    //@ExperimentalUseCaseGroupLifecycle
    override fun bindAllCameraXUseCases() {
        provideImageUseCase()
        displayManager.registerDisplayListener(this ,null )
    }


    override fun scanDocument(bitmap: Bitmap){


       executors.submit(
           Runnable {
               visionImageProcessor
                   .processImageProxy(bitmap)

           }) }


    @ExperimentalGetImage
    @ExperimentalUseCaseGroup
//    @ExperimentalUseCaseGroupLifecycle
    override fun provideImageUseCase() {

      val preview =  providePreviewUseCase()

        imageCapture = ImageCapture.Builder().setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()

        unbindAll()

        super.bindLifecycleView(imageCapture!!,preview)
        provideSurface(preview)


    }
//
//    override fun bindLifecycleView(previewUseCase: UseCase, useCase2: UseCase) {
//        provideImageUseCase()
//    }



    override fun getProgressLiveData(): LiveData<Boolean> {
        return progressStatus }


    override fun getTranslatedLiveData(): LiveData<String?> =translateText
    override fun testClick() {

    }

    override fun onDisplayAdded(displayId: Int) {
        println("DIsplay Added")
    }

    override fun onDisplayRemoved(displayId: Int) {

        println("DIsplay removed")

    }

    override fun onDisplayChanged(displayId: Int) {
        println("Display changed")



    }


    override  fun provideSurface(preview: Preview){
        mScanFragmentDataBinding?.include2?.previewFinder?.run{

            println("Provide Surface")

            preview.setSurfaceProvider(this.surfaceProvider)


        }?:throw NullPointerException("provide Surface Camera Provider must not be null")



    }



    override fun translate(result: String) {
        translateText.postValue(result)
    }


}