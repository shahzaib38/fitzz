package imagetrack.app.trackobject.camera_features

import android.graphics.Bitmap
import android.hardware.display.DisplayManager
import android.view.ScaleGestureDetector
import androidx.camera.core.*
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData

interface ICamera : LifecycleObserver ,
    ScaleGestureDetector.OnScaleGestureListener,
 DisplayManager.DisplayListener
{

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
    fun getProgressLiveData() :LiveData<Boolean>
    fun enableTorch()
    fun setZoomRatio(delta : Float)
    fun startFocusAndMetering(point: MeteringPoint)
    fun getTranslatedLiveData(): LiveData<String?>
    fun testClick()


//        companion object {
//
//        @Volatile
//        private var INSTANCE: ICamera? = null
//
//        fun getInstance(cameraMetaData: CameraMetaData): ICamera =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: ScanningCamera(cameraMetaData).also { INSTANCE = it }
//            }
//    }
//
//    object CameraFactory{
//
//
//        @Volatile
//        private var INSTANCE: ICamera? = null
//
//        fun getScanInstance(cameraMetaData: CameraMetaData): ICamera =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: ScanningCamera(cameraMetaData).also { INSTANCE = it }
//            }
//
//        fun getLiveInstance(cameraMetaData: CameraMetaData): ICamera =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: LiveCameraX(cameraMetaData).also { INSTANCE = it }
//            }
//
//
//
//    }


//
//    interface CallBack{
//
//        fun translate(result :String )
//
//    }






}