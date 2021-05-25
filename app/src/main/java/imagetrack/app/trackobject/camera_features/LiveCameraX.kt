package imagetrack.app.trackobject.camera_features

import android.content.Context
import android.graphics.Bitmap
import android.util.Size
import androidx.camera.core.*
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.camera.view.PreviewView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import imagetrack.app.TextAnalysis
import imagetrack.app.text_recognition.TextProcessAdapterFactory
import imagetrack.app.view.GraphicOverlay
import java.util.concurrent.Executors

@ExperimentalGetImage
@ExperimentalUseCaseGroup
@ExperimentalUseCaseGroupLifecycle
internal class LiveCameraX private constructor(private val context: Context, private val graphics: GraphicOverlay, lifecycleOwner: LifecycleOwner
                 ,private val  previewView: PreviewView): BaseCameraX(context , graphics,lifecycleOwner,previewView)
             ,IImageAnalysisUseCase{




         private  var visionImageProcessor= TextProcessAdapterFactory.createOnDeviceTextRecognizer(context)

    var fragmentManager :  FragmentManager? = null



    override  fun setFragmentManagerr(fragmentManager  : FragmentManager){
        this.fragmentManager = fragmentManager
    }

    override fun stop() {
        visionImageProcessor.run {
            this.stop() } }


    override fun bindAllCameraXUseCases() {
        super.bindAllCameraXUseCases()
        bindImageAnalysis()
    }

    override fun scanDocument(bitmap: Bitmap) {

    }


    override fun bindImageAnalysis() {
        val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(1000, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setBackgroundExecutor(Executors.newSingleThreadExecutor())
                .build()
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(),TextAnalysis.of(graphics,context))
        super.bindLifecycleView(imageAnalysis)
    }


    companion object{

        @Volatile
        private var instance  : LiveCameraX?=null

       internal  fun provideCamera(
            context: Context,
            graphics: GraphicOverlay,
            lifecycleOwner: LifecycleOwner,
            previewView: PreviewView
        ): LiveCameraX {
                 if (instance == null) {
                     return LiveCameraX(context , graphics ,lifecycleOwner,previewView) }
         return    LiveCameraX(context ,  graphics, lifecycleOwner, previewView)
        }


}

}