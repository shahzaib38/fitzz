package imagetrack.app.trackobject.camera_features

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.camera.core.AspectRatio
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.camera.view.PreviewView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import imagetrack.app.trackobject.ui.dialogs.ScanDialogFragment
import imagetrack.app.image_processing.ImageCaptureImpl
import imagetrack.app.text_recognition.TextProcessAdapterFactory
import imagetrack.app.view.GraphicOverlay
import java.util.concurrent.Executors



class ScanningCamera private constructor(
    private val context: Context,
    private val graphics: GraphicOverlay?,
    lifecycleOwner: LifecycleOwner, previewView: PreviewView, val progress: ProgressBar
) : BaseCameraX(
    context, graphics,
    lifecycleOwner, previewView
) ,ICaptureUseCase ,OpenDialog  , OnProgression{

    var fragmentManager :  FragmentManager? = null

    override  fun setFragmentManagerr(fragmentManager  : FragmentManager){
        this.fragmentManager = fragmentManager }


    override fun captureImage(){
        imageCapture?.takePicture(
            Executors.newSingleThreadExecutor(), ImageCaptureImpl.getInstance(
                context,
                this ,this)) }

    private var imageCapture : ImageCapture?=null

    override fun stop() {
        progress.visibility= View.GONE }

    @ExperimentalGetImage
    @ExperimentalUseCaseGroup
    @ExperimentalUseCaseGroupLifecycle
    override fun bindAllCameraXUseCases() {
        super.bindAllCameraXUseCases()
        provideImageUseCase()
    }

    override  fun startProgress(){
        Handler(Looper.getMainLooper()).post(Runnable {
            println("thread Name ${Thread.currentThread().name}")
            progress.visibility = View.VISIBLE
        })
}




   override fun scanDocument(bitmap : Bitmap){
      val executors = Executors.newSingleThreadExecutor()
       executors.submit(
           Runnable{ TextProcessAdapterFactory.createOnCloudTextRecognizer(context).processImageProxy(bitmap, this,this) } ) }


   override fun stopProgress(){
        Handler(Looper.getMainLooper()).post(Runnable {
            println("thread Name ${Thread.currentThread().name}")
            progress.visibility= View.GONE
        })

    }


    @ExperimentalGetImage
    @ExperimentalUseCaseGroup
    @ExperimentalUseCaseGroupLifecycle
    override fun provideImageUseCase() {
        imageCapture =
            ImageCapture.Builder().setTargetAspectRatio(AspectRatio.RATIO_16_9).also {}
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()

        super.bindLifecycleView(imageCapture!!)
    }

    companion object{

        @Volatile
        private var instance  : ScanningCamera?=null

        @ExperimentalGetImage
        @ExperimentalUseCaseGroup
        @ExperimentalUseCaseGroupLifecycle
        fun provideCamera(
            context: Context, graphics: GraphicOverlay?,
            lifecycleOwner: LifecycleOwner,
            previewView: PreviewView, progress: ProgressBar
        ):ICamera{

            if(instance ==null){
                return ScanningCamera(context, graphics, lifecycleOwner, previewView, progress) }
            return ScanningCamera(context, graphics, lifecycleOwner, previewView, progress) } }



    override fun openDialog(text: String) {

        fragmentManager?.let {fragmentManager->
            ScanDialogFragment.getInstance(text).showDialog(fragmentManager)
        }

    }

    override fun closeDialog() {
        ScanDialogFragment.getInstance("").dismiss()

    }





}