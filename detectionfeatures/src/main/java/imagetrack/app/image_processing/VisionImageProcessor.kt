package imagetrack.app.image_processing

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import imagetrack.app.trackobject.camera_features.OnProgression
import imagetrack.app.trackobject.camera_features.OpenDialog
import imagetrack.app.view.GraphicOverlay

interface VisionImageProcessor {

     fun processImageProxy(image: ImageProxy, graphicOverlay: GraphicOverlay)
    fun processImageProxy(image: ImageProxy,openDialog : OpenDialog ,onProgression: OnProgression  )
    fun processImageProxy(bitmap: Bitmap,openDialog : OpenDialog, onProgression: OnProgression )
    fun stop()



    interface Factory{
        fun createOnDeviceTextRecognizer(context  : Context) : VisionImageProcessor

        fun createOnCloudTextRecognizer(context : Context): VisionImageProcessor
    }


}