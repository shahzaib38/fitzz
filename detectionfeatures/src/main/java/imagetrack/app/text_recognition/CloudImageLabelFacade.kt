package imagetrack.app.text_recognition
//
//import com.google.android.gms.tasks.Task
//import com.google.firebase.ml.vision.FirebaseVision
//import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
//import com.google.firebase.ml.vision.common.FirebaseVisionImage
//
//object CloudImageLabelFacade {
//
//
//
//
//
//     fun labelImagesCloud(image: FirebaseVisionImage) : Task<MutableList<FirebaseVisionImageLabel>> {
//        // [START set_detector_options_cloud]
//        val options = FirebaseVisionCloudDetectorOptions.Builder()
//            .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
//            .setMaxResults(30)
//            .build()
//        // [END set_detector_options_cloud]
//
//        // [START get_detector_cloud]
//        val detector = FirebaseVision.getInstance()
//            .cloudImageLabeler
//
//
//        // Or, to change the default settings:
//        // val detector = FirebaseVision.getInstance()
//        //         .getCloudImageLabeler(options)
//        // [END get_detector_cloud]
//
//        // [START run_detector_cloud]
//       return  detector.processImage(image)
//
//        // [END run_detector_cloud]
//    }
//
//
//}