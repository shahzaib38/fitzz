package imagetrack.app.text_recognition

//import android.content.Context
//import android.util.Log
//import com.google.android.gms.tasks.Task
//import com.google.mlkit.vision.common.InputImage
//import com.google.mlkit.vision.label.ImageLabel
//import com.google.mlkit.vision.label.ImageLabeling
//import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
//import com.google.mlkit.vision.text.Text
//import com.google.mlkit.vision.text.TextRecognition
//import com.google.mlkit.vision.text.TextRecognizer
//import image.app.trackobject.camera_features.OpenDialog
//import image.app.view.GraphicOverlay
//import image.app.image_processing.VisionProcessorBase
//import java.util.concurrent.locks.Lock
//import java.util.concurrent.locks.ReentrantLock
//
//
//
//
//class TextRecognitionProcessor  constructor(context: Context) : VisionProcessorBase<MutableList<ImageLabel>>(context) {
//
//
//     val textRecognizer: TextRecognizer=TextRecognition.getClient()
//    val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
//
//
//    override fun stop() {
//        super.stop()
//        labeler.close() }
//
//    override fun detectInImage(image: InputImage): Task<MutableList<ImageLabel>> {
//
//
//
//        return labeler.process(image)
//    }
//
//    override fun onSuccess(results: MutableList<ImageLabel>, graphicOverlay: GraphicOverlay) {
//      //  graphicOverlay.clearAll()
//      //  graphicOverlay.add(results)
//    }
//
//    override fun onFailure(e: Exception) {
//
//        Log.w(TAG, "Text detection failed.$e")
//
//    }
//
//
//    companion object {
//        private const val TAG = "TextRecProcessor"
//        private val lock : Lock =ReentrantLock()
//
//        private fun logExtrasForTesting(text: Text?) {
//
//            if(text!=null){
//
//                println("text   ${text.text}")
//
//            }
//
////
////            if (text != null) {
////                println(
////                    "Detected text has : " + text.textBlocks.size + " blocks"
////                )
////                for (i in text.textBlocks.indices) {
////                    val lines = text.textBlocks[i].lines
////                    println(
////                        String.format("Detected text block %d has %d lines", i, lines.size))
////                    for (j in lines.indices) {
////                        val elements =
////                            lines[j].elements
////                        println(
////                            String.format("Detected text line %d has %d elements", j, elements.size)
////                        )
////                        for (k in elements.indices) {
////                            val element = elements[k]
////                            println(
////                                String.format("Detected text element %d says: %s", k, element.text)
////                            )
////                            println(String.format(
////                                    "Detected text element %d has a bounding box: %s",
////                                    k, element.boundingBox!!.flattenToString()
////                                )
////                            )
////                            println(String.format(
////                                    "Expected corner point size is 4, get %d", element.cornerPoints!!.size
////                                )
////                            )
////                            for (point in element.cornerPoints!!) {
////                                println(
////                                    String.format(
////                                        "Corner point for element %d is located at: x - %d, y = %d",
////                                        k, point.x, point.y
////                                    )
////                                )
////                            }
////                        }
////                    }
////                }
////            }
//        }
//    }
//
//
//    override fun onSuccess(results: MutableList<ImageLabel>, openDialog: OpenDialog) {
//
//    }
//
//}