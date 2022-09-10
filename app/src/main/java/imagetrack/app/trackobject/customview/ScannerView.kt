package imagetrack.app.trackobject.customview

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class ScannerView @JvmOverloads  constructor(context:Context ,attr:AttributeSet?=null  ,defStyle:Int = 0 ) : View(context ,attr ,defStyle) {


   private   var scannerHeight = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

         scannerHeight = (height *2)/100



    }

     fun animateScanner(callBack :()->Unit  ){


        ValueAnimator.ofInt(-300 , height +300).apply{

            this.duration = 4000
            this.interpolator = LinearInterpolator()


        addUpdateListener {

            dynamicHeight = it.animatedValue as Int
            invalidate()

        }

            addListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator?) {
                    paint.alpha = 255

                }

                override fun onAnimationEnd(animation: Animator?) {
                    paint.alpha = 0

                    callBack.invoke()
                }

                override fun onAnimationCancel(animation: Animator?) {


                }

                override fun onAnimationRepeat(animation: Animator?) {

                }


            })

            start() } }


    private var dynamicHeight = -300
    private val paint = Paint().apply {

        this.color = Color.parseColor("#0095FD")
        this.isAntiAlias = true
        this.alpha = 0
    }

    val rect = Rect()


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas == null) return

        rect.left = 0
        rect.top = dynamicHeight
        rect.right = width
        rect.bottom = dynamicHeight + scannerHeight
        canvas.drawRect(rect ,paint  )

    }








}