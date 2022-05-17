package imagetrack.app.image_processing

import android.content.Context
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import imagetrack.app.CallBack
import imagetrack.app.view.GraphicOverlay
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CompletableFuture


class DeviceTextRecognizer(context : Context )  : VisionProcessorBase<FirebaseVisionText>(context)  {

    private val detector = FirebaseVision.getInstance().onDeviceTextRecognizer



    private val translateTextLiveData = MutableLiveData<String>()

    override fun getTranslateTextLiveData(): LiveData<String> =translateTextLiveData

    override fun stop() {
      super.stop()
        detector.close()
    }





    override fun onSuccess(results: FirebaseVisionText, graphicOverlay: GraphicOverlay) {
    }

    override fun onSuccess(results: FirebaseVisionText) {
       // translateTextLiveData.postValue(results.text)
        translateTextLiveData.postValue(results.text)



    }

    override fun onFailure(e: Exception) {
        val message = e.message
        if(message!=null) {

            translateTextLiveData.postValue(message)


        }

    }

    override fun detectInImage(image: FirebaseVisionImage): Task<FirebaseVisionText> {

        Log.i("Thread","Device Text Recogni "+Thread.currentThread().name)
        return detector.processImage(image)
    }


    override fun requestDetectInImageFuture(firebaseVisionImage: FirebaseVisionImage): CompletableFuture<String> {
        println("requestDetectInImageFuture "+Thread.currentThread().name)

        val scanCompletableFuture =CompletableFuture<String>()
        detectInImage(firebaseVisionImage).addOnSuccessListener {

            if(it!=null) {
                scanCompletableFuture.complete(it.text) }
        }.addOnFailureListener {
            scanCompletableFuture.exceptionally {
                it.message
            } }

        return scanCompletableFuture }



    @ExperimentalCoroutinesApi
    override suspend fun requestDetectInImageFlow(firebaseVisionImage: FirebaseVisionImage ,imageProxy: ImageProxy)  : String {
//        println("requestDetectInImageFlow " + Thread.currentThread().name)
//        detectInImage(firebaseVisionImage).addOnSuccessListener {
//                 offer(it.text)
//        }.addOnFailureListener {
//            close(it) }
//
//   awaitClose {
//
//     //  detector.close()
//   }

        val deferred = CompletableDeferred<String>()

        return ""
   }

    override suspend fun requestDetectTextFromImage(firebaseVisionImage: FirebaseVisionImage): String {


       return  ""
    }


}









