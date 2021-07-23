package imagetrack.app.image_processing

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import imagetrack.app.trackobject.camera_features.OpenDialog
import imagetrack.app.view.GraphicOverlay

class DeviceTextRecognizer(context : Context)  : VisionProcessorBase<FirebaseVisionText>(context)  {

    val detector = FirebaseVision.getInstance().onDeviceTextRecognizer



    override fun stop() {
      super.stop()
        detector.close()
    }

    override fun onFailure(e: Exception) {

    }

    override fun onSuccess(results: FirebaseVisionText, graphicOverlay: GraphicOverlay) {
    }

    override fun onSuccess(results: FirebaseVisionText, openDialog: OpenDialog) {
        openDialog.openDialog(results.text) }

    override fun onFailure(e: Exception, openDialog: OpenDialog) {
        val message = e.message
        if(message!=null) {
            openDialog.openDialog(message)
        }
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<FirebaseVisionText> {

        Log.i("Thread","Device Text Recogni "+Thread.currentThread().name)
        return detector.processImage(image)
    }


}