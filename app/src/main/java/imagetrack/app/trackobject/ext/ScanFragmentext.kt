package imagetrack.app.trackobject.ext

import android.content.Context
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.camera.core.Camera
import androidx.camera.core.FocusMeteringAction
import imagetrack.app.trackobject.databinding.ScanFragmentDataBinding


 fun ScanFragmentDataBinding.scaleGestureDetector(context : Context, camera : Camera){

    val scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener{
        override fun onScale(detector: ScaleGestureDetector?): Boolean {

            val delta = detector?.scaleFactor ?: return false
            val currentZoomRatio = camera.cameraInfo.zoomState.value?.zoomRatio ?: 0F
            camera.cameraControl.setZoomRatio(currentZoomRatio * delta)

            return true }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {

            return true }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
        }
    }
    )

    this.include2.previewFinder.apply {
        setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> return@setOnTouchListener true
                MotionEvent.ACTION_UP -> {
                    val factory = this.meteringPointFactory
                    val point = factory.createPoint(event.x, event.y)

                    val action = FocusMeteringAction.Builder(point).build()
                    camera.cameraControl.startFocusAndMetering(action)

                    return@setOnTouchListener true }
                else -> return@setOnTouchListener false }
        } }


}
