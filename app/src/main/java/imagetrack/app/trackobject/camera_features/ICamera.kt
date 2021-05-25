package imagetrack.app.trackobject.camera_features

import android.content.Context
import android.graphics.Bitmap
import android.widget.ProgressBar
import androidx.camera.core.Camera
import androidx.camera.core.MeteringPoint
import androidx.camera.view.PreviewView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import imagetrack.app.view.GraphicOverlay

interface ICamera : LifecycleObserver {

     fun startCamera()
     fun getCamera():Camera?
     fun onCameraDestroy()
     fun onCameraResume()
     fun onCameraPause()
    fun scanDocument(bitmap : Bitmap)

     fun setFragmentManagerr(fragmentManager : FragmentManager)

    fun enableTorch()




    fun setZoomRatio(delta : Float)
    fun startFocusAndMetering(point: MeteringPoint)

    interface Factory {
        fun setCamera(context : Context, graphics : GraphicOverlay?, lifecycleOwner  : LifecycleOwner, previewView: PreviewView,progressBar: ProgressBar):ICamera?
    }


}