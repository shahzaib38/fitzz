package imagetrack.app.image_processing

import android.content.Context
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import imagetrack.app.text_recognition.TextProcessAdapterFactory
import imagetrack.app.trackobject.camera_features.OnProgression
import imagetrack.app.trackobject.camera_features.OpenDialog


class ImageCaptureImpl(private val context: Context, private val openDialog: OpenDialog, private val onProgression: OnProgression) : ImageCapture.OnImageCapturedCallback(){

    @ExperimentalGetImage
    override fun onCaptureSuccess(image: ImageProxy) {
        println("ImageCaptureImpl Thread  Name ${Thread.currentThread().name}")
        TextProcessAdapterFactory.createOnDeviceTextRecognizer(context).processImageProxy(image, openDialog,onProgression) }




    override fun onError(exception: ImageCaptureException) {
        println("image Error ")
        onProgression.stopProgress() }


    companion object {
     var imageCapture :ImageCapture.OnImageCapturedCallback?=null
        fun capture(context: Context, openDialog: OpenDialog, onProgression: OnProgression)  :ImageCapture.OnImageCapturedCallback{
            if(imageCapture==null){
                imageCapture = ImageCaptureImpl(context, openDialog, onProgression)
                return imageCapture!! }
            return imageCapture!!
        }

    }

}