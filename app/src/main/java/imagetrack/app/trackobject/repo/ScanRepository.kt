package imagetrack.app.trackobject.repo

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import imagetrack.app.image_processing.VisionImageProcessor
import imagetrack.app.translate.TranslateApi
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.CompletableFuture
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScanRepository @Inject constructor(private val visionImageProcessor: VisionImageProcessor ,

                                         private val translateApi : TranslateApi
                                         ): BaseRepository() {


    val translateLiveData = visionImageProcessor.getTranslateTextLiveData()
    val progressLiveData =visionImageProcessor.getProgressLiveData()



   suspend fun scanText(bitmap: Bitmap ) =visionImageProcessor.processImageProxy(bitmap)



    suspend fun getUsers(name: Map<String, String>) = translateApi.getData(name)


    suspend fun scanText(image: ImageProxy) = visionImageProcessor.processImageProxy(image)


    fun stopImageProcessor() {
        visionImageProcessor.stop()
    }

    fun clear() {

        visionImageProcessor.clear()
    }


}