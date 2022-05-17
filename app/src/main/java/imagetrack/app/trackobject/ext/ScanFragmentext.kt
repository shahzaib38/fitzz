package imagetrack.app.trackobject.ext

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import androidx.camera.core.Camera
import androidx.camera.core.FocusMeteringAction
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.ScanFragmentDataBinding
import kotlinx.android.synthetic.main.preview_layout.view.*
import kotlinx.android.synthetic.main.scan_fragment.view.*


fun ScanFragmentDataBinding.scaleGestureDetector(context : Context, camera : Camera){

//    val scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener{
//        override fun onScale(detector: ScaleGestureDetector?): Boolean {
//
//            val delta = detector?.scaleFactor ?: return false
//            val currentZoomRatio = camera.cameraInfo.zoomState.value?.zoomRatio ?: 0F
//            camera.cameraControl.setZoomRatio(currentZoomRatio * delta)
//
//            return true }
//
//        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
//
//            return true }
//
//        override fun onScaleEnd(detector: ScaleGestureDetector?) {
//        }
//    }
//    )

//  val fouusRing =  this.include2.mainId.focusRing
//    this.include2.previewFinder.apply {
//
//
//
//        setOnTouchListener { view, event ->
//            scaleGestureDetector.onTouchEvent(event)
//
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> return@setOnTouchListener true
//                MotionEvent.ACTION_UP -> {
//
//
//
//                   fouusRing.apply {
//
//
//                     val x = event.rawX  - this.width/2
//                       val y  = event.rawY  - this.width/2
//                         this.x =x
//                         this.y =y
//
//                    }
//
//                    val valueAnimator = ValueAnimator.ofFloat(1.0f ,0.8f )
//                     valueAnimator.duration = 350
//
//                    valueAnimator.start()
//                    fouusRing.visibility = View.VISIBLE
//
//                    valueAnimator.addListener(object : Animator.AnimatorListener{
//                        override fun onAnimationStart(animation: Animator?) {
//
//                        }
//
//                        override fun onAnimationEnd(animation: Animator?) {
//
//                          println("ANIMATIONN END")
//                            fouusRing.visibility =View.GONE
//                        }
//
//                        override fun onAnimationCancel(animation: Animator?) {
//                        }
//
//                        override fun onAnimationRepeat(animation: Animator?) {
//                        }
//
//
//                    })
//
//                    valueAnimator.addUpdateListener {
//
//
//                    val animatedValues =    it.animatedValue as Float
//
//                        fouusRing.apply {
//                            this.scaleX = animatedValues
//                            this.scaleY = animatedValues }
//
//
//
//                        }
//
//
//                    val factory = this.meteringPointFactory
//                    val point = factory.createPoint(event.x, event.y)
//
//                    val action = FocusMeteringAction.Builder(point).build()
//                    camera.cameraControl.startFocusAndMetering(action)
//                    view.performClick();
//                    return@setOnTouchListener true }
//                else -> return@setOnTouchListener false }
//
//
//        }
//
//
//    }


}
