package imagetrack.app.image_processing

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.GuardedBy
import androidx.annotation.RequiresApi
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import imagetrack.app.trackobject.camera_features.OnProgression
import imagetrack.app.trackobject.camera_features.OpenDialog
import imagetrack.app.utils.FrameMetadata
import imagetrack.app.view.GraphicOverlay

abstract class VisionProcessorBase<T>(private val context: Context) : VisionImageProcessor {

 companion object {
//  const val MANUAL_TESTING_LOG = "LogTagForTest"
  private const val TAG = "VisionProcessorBase"
 }


 private val executor = ScopedExecutor(TaskExecutors.MAIN_THREAD)

 private var isShutdown = false



 @GuardedBy("this")
 private var processingMetaData: FrameMetadata? = null


 private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
  0 -> FirebaseVisionImageMetadata.ROTATION_0
  90 -> FirebaseVisionImageMetadata.ROTATION_90
  180 -> FirebaseVisionImageMetadata.ROTATION_180
  270 -> FirebaseVisionImageMetadata.ROTATION_270
  else -> throw Exception("Rotation must be 0, 90, 180, or 270.") }


 @ExperimentalGetImage
 override fun processImageProxy(image: ImageProxy,openDialog : OpenDialog, onProgression: OnProgression ) {
  if (isShutdown) {
   return
  }

  onProgression.startProgress()
 val mediaImage =  image.image
 val rotationDegree = image.imageInfo.rotationDegrees
  val degree = degreesToFirebaseRotation(rotationDegree)
  val firebaseVisionImage=   FirebaseVisionImage.fromMediaImage(mediaImage!!, degree)
  requestDetectInImage(firebaseVisionImage, openDialog, onProgression)
    .addOnCompleteListener { image.close() }
 }

 override fun processImageProxy(
  bitmap: Bitmap,
  openDialog: OpenDialog,
  onProgression: OnProgression
 ) {

    onProgression.startProgress()
   val firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap)
  requestDetectInImage(firebaseVisionImage, openDialog, onProgression)
 }

 // -----------------Code for processing live preview frame from CameraX API-----------------------
 @RequiresApi(Build.VERSION_CODES.KITKAT)
 @ExperimentalGetImage
 override fun processImageProxy(image: ImageProxy, graphicOverlay: GraphicOverlay) {     //Dynamic Aurgument
//  val frameStartMs = SystemClock.elapsedRealtime()
//       println("Frame Rate $frameStartMs")
//     if (isShutdown) {
//   return
//  }
//  var bitmap: Bitmap? = null
//
//  requestDetectInImage(InputImage.fromMediaImage(image.image!! , image.imageInfo.rotationDegrees) ,
//   graphicOverlay,bitmap, true, frameStartMs).addOnCompleteListener {
//   image.close()
//  }
 }





 private fun requestDetectInImage(firebaseVisionImage : FirebaseVisionImage,   openDialog : OpenDialog,onProgression: OnProgression): Task<T> {

  return detectInImage(firebaseVisionImage).addOnSuccessListener{ results: T ->
   onProgression.stopProgress()
   this@VisionProcessorBase.onSuccess(results,openDialog)

  }.addOnFailureListener { e: Exception ->
   onProgression.stopProgress()
   this@VisionProcessorBase.onFailure(e,openDialog)

  }

 }

 override fun stop() {
  executor.shutdown()
  isShutdown = true
  }




 protected abstract fun detectInImage(image: FirebaseVisionImage ): Task<T>  //Template Method

 protected abstract fun onSuccess(results: T, graphicOverlay: GraphicOverlay)   //Template Method
 protected abstract fun onSuccess(results: T,openDialog : OpenDialog)
 protected abstract fun onFailure(e: Exception)    //Template Method

 protected open fun onFailure(e: Exception, openDialog : OpenDialog){

 }
}