package imagetrack.app.trackobject.ext

import android.animation.Animator
import android.view.View
import android.widget.ImageView


     fun ImageView.animateFocusRing(x1: Float, y1: Float){
        val width = width
        val height = height
        x = (x1 - width / 2)
        y = (y1 - height / 2)
        println("X   $x")
        println("Y   $y")
        visibility = View.VISIBLE
        alpha = 1F
        animate().setStartDelay(500).setDuration(300).alpha(0F)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    visibility = View.INVISIBLE
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationRepeat(animation: Animator?) {

                }

            })

    }