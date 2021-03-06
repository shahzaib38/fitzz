package imagetrack.app.ext

import android.app.Activity
import androidx.fragment.app.Fragment
import imagetrack.app.utils.CameraPermissions

fun Fragment.requestCameraPermission() {
    requestPermissions(CameraPermissions.CAMERA_PERMISSION_ARRAY , CameraPermissions.CAMERA_PERMISSION)
}


fun Activity.requestCameraPermission() {
    requestPermissions(CameraPermissions.CAMERA_PERMISSION_ARRAY , CameraPermissions.CAMERA_PERMISSION)
}

