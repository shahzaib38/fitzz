package imagetrack.app.text_recognition

import android.content.Context
import imagetrack.app.image_processing.CloudTextRecognition
import imagetrack.app.image_processing.DeviceTextRecognizer
import imagetrack.app.image_processing.VisionImageProcessor

object TextProcessAdapterFactory : VisionImageProcessor.Factory {


    @Volatile
    private var deviceTextRecognizer : DeviceTextRecognizer?=null


    @Volatile
    private var cloudTextRecognition : CloudTextRecognition?=null

    override fun createOnDeviceTextRecognizer(context : Context): VisionImageProcessor {

        if(deviceTextRecognizer==null){
            synchronized(TextProcessAdapterFactory::class){
            if(deviceTextRecognizer==null){
                deviceTextRecognizer = DeviceTextRecognizer(context) } }
        }
        return deviceTextRecognizer!! }

    override fun createOnCloudTextRecognizer(context: Context): VisionImageProcessor {
        if(cloudTextRecognition==null){
            synchronized(TextProcessAdapterFactory::class){
                if(cloudTextRecognition==null){
                    cloudTextRecognition = CloudTextRecognition(context) } }
        }
        return cloudTextRecognition!!

    }
}