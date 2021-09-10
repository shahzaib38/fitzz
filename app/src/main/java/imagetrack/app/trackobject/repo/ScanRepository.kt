package imagetrack.app.trackobject.repo

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import imagetrack.app.image_processing.VisionImageProcessor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScanRepository @Inject constructor(private val visionImageProcessor: VisionImageProcessor): BaseRepository() {


    val translateLiveData = visionImageProcessor.getTranslateTextLiveData()
    val progressLiveData =visionImageProcessor.getProgressLiveData()



    fun scanText(bitmap: Bitmap ){
        visionImageProcessor.processImageProxy(bitmap)
    }


    fun scanText(image: ImageProxy){

        visionImageProcessor.processImageProxy(image)
    }

    fun stopImageProcessor() {
        visionImageProcessor.stop()
    }


}