package imagetrack.app.image_processing

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import imagetrack.app.trackobject.camera_features.OpenDialog
import imagetrack.app.view.GraphicOverlay


    class CloudTextRecognition(context: Context)  : VisionProcessorBase<FirebaseVisionText>(context)  {

        val detector = FirebaseVision.getInstance().cloudTextRecognizer


        override fun stop() {
            super.stop()
            detector.close()
        }

        override fun detectInImage(firebaseVisionImage: FirebaseVisionImage ): Task<FirebaseVisionText> {
            return detector.processImage(firebaseVisionImage)
        }


        override fun onFailure(e: Exception) {

        }

    override fun onSuccess(results: FirebaseVisionText, graphicOverlay: GraphicOverlay) {
    }

    override fun onSuccess(results: FirebaseVisionText, openDialog: OpenDialog) {
        println(results.text)
        openDialog.openDialog(results.text) }

    override fun onFailure(e: Exception, openDialog: OpenDialog) {

      val message =   e.message

        println(message)
        if(message!=null) {
            openDialog.openDialog(message)
        }
        }


}