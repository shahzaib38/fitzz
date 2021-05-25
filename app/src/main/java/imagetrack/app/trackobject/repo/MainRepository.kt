package imagetrack.app.trackobject.repo

import android.content.Context
import android.widget.ProgressBar
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import imagetrack.app.trackobject.camera_features.CameraProvider
import imagetrack.app.trackobject.camera_features.ICamera
import imagetrack.app.translate.TranslateApi
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRepository @Inject constructor(private val translateApi : TranslateApi): BaseRepository() {

//
//    @ExperimentalUseCaseGroup
//    @ExperimentalUseCaseGroupLifecycle
//    @ExperimentalGetImage
//    fun provideCamera(context : Context, graphics : GraphicOverlay, lifecycleOwner  : LifecycleOwner, previewView: PreviewView):ICamera?{
//
//
//       return  CameraProvider.setCamera(context , graphics , lifecycleOwner , previewView,progress) }




    @ExperimentalUseCaseGroup
    @ExperimentalUseCaseGroupLifecycle
    @ExperimentalGetImage
    fun provideScanCamera(context : Context, lifecycleOwner  : LifecycleOwner, previewView: PreviewView,progress : ProgressBar):ICamera?{


        return  CameraProvider.setCamera(context ,  null,lifecycleOwner , previewView,progress) }


    suspend fun getUsers(name: Map<String, String>) = translateApi.getData(name)


}