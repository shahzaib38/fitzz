package imagetrack.app.trackobject.camera_features

import android.content.Context
import android.graphics.Bitmap
import android.view.ScaleGestureDetector
import android.widget.ProgressBar
import androidx.camera.core.*
import androidx.camera.view.PreviewView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import imagetrack.app.view.GraphicOverlay

interface ICamera : LifecycleObserver ,ScaleGestureDetector.OnScaleGestureListener {

     fun startCamera()
     fun getCamera():Camera?
     fun onCameraDestroy()
     fun onCameraResume()
     fun onCameraPause()
    fun scanDocument(bitmap : Bitmap)
     fun getCameraInfo() : CameraInfo?
      fun getCameraControl() :CameraControl?
     fun getTorchState(): LiveData<Int>?
     fun getZoomState(): LiveData<ZoomState>?

    fun setFragmentManagerr(fragmentManager : FragmentManager)

    fun enableTorch()




    fun setZoomRatio(delta : Float)
    fun startFocusAndMetering(point: MeteringPoint)

    interface Factory {
        fun setCamera(context : Context, graphics : GraphicOverlay?, lifecycleOwner  : LifecycleOwner, previewView: PreviewView,progressBar: ProgressBar):ICamera?
    }


}