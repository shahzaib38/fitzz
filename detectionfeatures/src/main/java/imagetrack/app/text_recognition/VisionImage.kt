package imagetrack.app.text_recognition
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.media.Image
//import android.net.Uri
//import android.os.Build
//import androidx.annotation.RequiresApi
//import com.google.firebase.ml.vision.common.FirebaseVisionImage
//import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
//import java.io.IOException
//import java.nio.ByteBuffer
//
//object VisionImage {
//
//
//
//    private fun imageFromBitmap(bitmap: Bitmap) :FirebaseVisionImage{
//        // [START image_from_bitmap]
//        return FirebaseVisionImage.fromBitmap(bitmap)
//        // [END image_from_bitmap]
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//     fun imageFromMediaImage(mediaImage: Image, rotation: Int) :FirebaseVisionImage{
//        // [START image_from_media_image]
//        return  FirebaseVisionImage.fromMediaImage(mediaImage, rotation)
//        // [END image_from_media_image]
//    }
//
//    private fun imageFromBuffer(buffer: ByteBuffer, rotation: Int) :FirebaseVisionImage{
//        // [START set_metadata]
//        val metadata = FirebaseVisionImageMetadata.Builder()
//            .setWidth(480) // 480x360 is typically sufficient for
//            .setHeight(360) // image recognition
//            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
//            .setRotation(rotation)
//            .build()
//        // [END set_metadata]
//        // [START image_from_buffer]
//        return  FirebaseVisionImage.fromByteBuffer(buffer, metadata)
//        // [END image_from_buffer]
//    }
//
//    private fun imageFromArray(byteArray: ByteArray, rotation: Int) :FirebaseVisionImage{
//        val metadata = FirebaseVisionImageMetadata.Builder()
//            .setWidth(480) // 480x360 is typically sufficient for
//            .setHeight(360) // image recognition
//            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
//            .setRotation(rotation)
//            .build()
//        // [START image_from_array]
//        return  FirebaseVisionImage.fromByteArray(byteArray, metadata)
//        // [END image_from_array]
//
//    }
//
//    private fun imageFromPath(context: Context, uri: Uri) {
//        // [START image_from_path]
//        val image: FirebaseVisionImage
//        try {
//            image = FirebaseVisionImage.fromFilePath(context, uri)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        // [END image_from_path]
//    }
//
//}