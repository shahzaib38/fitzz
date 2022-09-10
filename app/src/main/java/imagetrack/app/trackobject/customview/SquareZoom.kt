package imagetrack.app.trackobject.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator

class SquareZoom @JvmOverloads constructor(context: Context, attr: AttributeSet?=null,defStyle:Int=0  ) :
    View(context ,attr ,defStyle ) {



    private var squareRect = RectF(0f,0f,0f,0f)

    //DYNAMIC Color
    private var _DYNAMIC_COLOR = Color.parseColor("#0095FD")

        private var side_lenght = 0



    private var alpha = 0


    private var paint = Paint().apply {
        this.color = _DYNAMIC_COLOR
        this.style  = Paint.Style.STROKE
        this.strokeWidth =5f
          this.alpha = alpha
        this.isAntiAlias = true }



    private var twenty_percent = 0
     private var eighty_percent = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


        side_lenght = Math.min( width , height )
        twenty_percent  = (side_lenght *20)/100
        eighty_percent  = (side_lenght *80)/100
    }


    fun onZoom(zoomRatio :Float ){

        val half =       twenty_percent/2
        val eighty_ratio  =(eighty_percent/2  - eighty_percent/2 /zoomRatio)
        val ratio = half + (eighty_ratio)

        squareRect.apply {
            this.left  =  width/2  - ratio
            this.right = width/2  + ratio
            this.top  = height/2  - ratio
            this.bottom = height/2  + ratio

        }
        alpha =255


        animateAlpha()


    }

   private  fun animateAlpha(){

        ValueAnimator.ofInt(255 , 0).apply {


            this.duration = 500
            this.interpolator =LinearInterpolator()
            this.repeatMode = ValueAnimator.RESTART


            addUpdateListener{

                alpha = it.animatedValue as Int

                invalidate()

            }



            start()

        }



    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas ==null)return



        paint.alpha = alpha
        paint.strokeWidth =  width/100f

        canvas.drawRect(squareRect ,paint )

    }






}