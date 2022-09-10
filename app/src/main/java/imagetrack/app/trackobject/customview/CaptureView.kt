package imagetrack.app.trackobject.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator

class CaptureView @JvmOverloads constructor(context : Context , attr : AttributeSet?=null ,defStyle:Int = 0 ) : View(context ,attr ,defStyle) {


    private val paint = Paint().apply {

        this.isAntiAlias = true
        this.color = Color.BLACK
        this.alpha = 0 }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


    }

    fun animateCapture(){

        ValueAnimator.ofInt(255, 0).apply {
            this.duration = 700
            this.interpolator = LinearInterpolator()



            addUpdateListener{

                Log.i("test","animated caprre"+it.animatedValue)

                paint.alpha = it.animatedValue as Int
                invalidate()
            }




            this.start()
        }



    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas==null)return

        val rect = Rect(0,0,width,height)
        canvas.drawRect(rect,paint)



    }






}