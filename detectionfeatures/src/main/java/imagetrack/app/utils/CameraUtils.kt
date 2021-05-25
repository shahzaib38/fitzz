package imagetrack.app.utils


import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider

object CameraUtils {


    val BACKCAMERA = CameraSelector.LENS_FACING_BACK
    val FRONTCAMERA = CameraSelector.LENS_FACING_FRONT


     fun hasBackCamera(cameraProvider :ProcessCameraProvider): Boolean {
        return cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false }

     fun hasFrontCamera(cameraProvider :ProcessCameraProvider): Boolean {
        return cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ?: false }


//    private fun aspectRatio(width: Int, height: Int): Int {
//        val previewRatio = max(width, height).toDouble() / min(width, height)
//        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
//            return AspectRatio.RATIO_4_3
//        }
//        return AspectRatio.RATIO_16_9
//    }


}