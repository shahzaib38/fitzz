package imagetrack.app.trackobject.animations

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView



    fun TextView.animate(anim :Int){
     val animation=  AnimationUtils.loadAnimation(context ,anim)

        animation.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                visibility = View.GONE

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

        startAnimation(animation)

    }


