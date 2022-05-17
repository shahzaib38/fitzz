package imagetrack.app.image_processing

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.CompletableFuture


sealed class Result {

    data class onSuccess<T>(var t: T) : Result()





    data class onShutDown<T>(var t: T) : Result()



}



interface VisionImageProcessor {

    suspend fun processImageProxy(imageProxy: ImageProxy): String

    suspend fun processImage(imageProxy:ImageProxy):String

      suspend fun processImageProxy(bitmap: Bitmap) :String


    fun stop()

    fun getProgressLiveData(): LiveData<Boolean>
    fun getTranslateTextLiveData() :LiveData<String>
    fun processImgeProxyFuture( bitmap: Bitmap): CompletableFuture<String>
    fun clear()


    // fun processImgeProxyFlow( bitmap: Bitmap): Flow<String>
}