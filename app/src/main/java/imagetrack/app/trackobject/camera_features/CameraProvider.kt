package imagetrack.app.trackobject.camera_features

import android.content.Context
import android.widget.ProgressBar
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import imagetrack.app.view.GraphicOverlay


@ExperimentalGetImage
@ExperimentalUseCaseGroup
@ExperimentalUseCaseGroupLifecycle
object CameraProvider :ICamera.Factory{

   override fun setCamera(context: Context, graphics: GraphicOverlay?, lifecycleOwner: LifecycleOwner, previewView: PreviewView,progress : ProgressBar):ICamera?{

       try {

           val icamera =  ScanningCamera.provideCamera(context ,null,lifecycleOwner,previewView,progress)

           icamera.startCamera()
         return  icamera
       } catch (c: NoSuchMethodException){
           println("NoSuchMethodException Provider ${c.message}")
       }catch (c: ClassNotFoundException){
           println("ClassNotFoundException Provider ${c.message}")
       }catch (c: IllegalArgumentException){
           println("IllegalArgumentException Provider ${c.message}") }

       return null }



}