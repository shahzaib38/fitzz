package imagetrack.app

import android.content.Context
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import imagetrack.app.trackobject.camera_features.ConditionUtils
import imagetrack.app.text_recognition.TextProcessAdapterFactory
import imagetrack.app.view.GraphicOverlay

@ExperimentalGetImage
class TextAnalysis(private val graphics : GraphicOverlay , private val  context: Context) :  ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        TextProcessAdapterFactory.createOnDeviceTextRecognizer(context).processImageProxy(image,graphics)
        println("Width ${image.width}")
        println("Height ${image.height}")



    }


    companion object {
        fun of(graphics : GraphicOverlay, context: Context):TextAnalysis{
            ConditionUtils.checkNotNull(context , "Context == null ")
             ConditionUtils.checkNotNull(graphics , "Graphics == null")
            return TextAnalysis(graphics ,context)} }

}