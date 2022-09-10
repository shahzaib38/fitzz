package imagetrack.app.trackobject.customview

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import imagetrack.app.trackobject.R

class FocusView  @JvmOverloads constructor( context : Context , attr :AttributeSet?=null , defStyle:Int =0   ) : View(context,attr ,defStyle ) {




    init {


        obtainViewStyle(context , attr ,defStyle)



    }


    enum class Focus{

        FOCUSED ,NOT_FOCUSED


    }

    private var quarterRadius :Float = 0f
    private var dynamicRadius : Float = 0f
    private var left:Float = 0f
    private var top :Float =0f
    private var focused = Focus.NOT_FOCUSED


    private val outerPaint = Paint().apply {
        this.isAntiAlias = true
        this.color = Color.WHITE
        this.style = Paint.Style.STROKE
        this.alpha = 0

    }

    private val innerPaint  = Paint().apply {
        this.isAntiAlias =true
        this.alpha = 0
        this.color = Color.WHITE
        this.style = Paint.Style.FILL }



    private fun obtainViewStyle(context:Context , attr: AttributeSet?, defStyle: Int ) {

        val typedArray = context.obtainStyledAttributes(attr,R.styleable.FocusView  ,defStyle,0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


        val width = MeasureSpec.getSize(widthMeasureSpec)

        val height = MeasureSpec.getSize(heightMeasureSpec)

        val half =   width/2.0f

        Log.i("test","half"+half)

        val circleRadius  = half/5

        val diameter = circleRadius * 2



        setMeasuredDimension(diameter.toInt()+5,diameter.toInt()+5)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)


        val half =   width/2.0f

        Log.i("test","half"+half)
        val circleRadius  = half/5

     //   quarterRadius = circleRadius

   //     dynamicRadius = circleRadius



    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        dynamicRadius = width/2.toFloat()


        this.left = width/2.toFloat()

        this.top = height/2.toFloat()

        outerPaint.alpha= 0
        innerPaint.alpha = 0
    }


     fun  focus(left:Float , top : Float){
         if(focused == Focus.FOCUSED)return

         this.x = left
         this.y = top

         outerPaint.alpha = 255
         innerPaint.alpha =255

         invalidate()
         animateFocus()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas==null) return


        canvas.drawCircle(
            width/2.toFloat(),
            height/2.toFloat() ,
             width/2.toFloat() -5f,
            outerPaint)

        canvas.drawCircle(width/2.toFloat(),
            height/2.toFloat() ,
            dynamicRadius -5f  ,
            innerPaint )


    }


  private   fun animateFocus(){


        ValueAnimator.ofFloat(width/2.toFloat() ,0f).apply {
            this.duration = 1500L
            this.interpolator = LinearInterpolator()


            addUpdateListener {
                dynamicRadius = it.animatedValue as Float


                invalidate()

            }

            addListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator?) {

                    focused = Focus.FOCUSED

                }

                override fun onAnimationEnd(animation: Animator?) {

                    focused = Focus.NOT_FOCUSED

                    innerPaint.alpha = 0
                    outerPaint.alpha = 0

                }

                override fun onAnimationCancel(animation: Animator?) {
                    focused = Focus.NOT_FOCUSED


                }

                override fun onAnimationRepeat(animation: Animator?) {

                }


            })


            start()
        }




  }


}