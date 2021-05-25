package imagetrack.app.text_recognition

import android.content.Context
import imagetrack.app.image_processing.CloudTextRecognition
import imagetrack.app.image_processing.DeviceTextRecognizer
import imagetrack.app.image_processing.VisionImageProcessor

object TextProcessAdapterFactory : VisionImageProcessor.Factory {

    override fun createOnDeviceTextRecognizer(context : Context): VisionImageProcessor {
        return DeviceTextRecognizer(context) }

    override fun createOnCloudTextRecognizer(context: Context): VisionImageProcessor {
        return CloudTextRecognition(context) }
}