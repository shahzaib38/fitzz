package imagetrack.app.trackobject.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator

class CameraButton @JvmOverloads constructor(context: Context, attr: AttributeSet?=null, defStyle:Int=0) : View(context ,attr,defStyle) {


    private var sizeLength :Float = 0f
    private var strokeWidth  :Float =0f
    private var innerLength :Float =0f
    private var changesize :Float = 0f
    private var dynamicLength :Float =0f


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val originalSize =  ( heightSize *15)/100

        setMeasuredDimension(originalSize,originalSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


        Log.i("height","height"+height)

      sizeLength =   (height * 60)/100f
        strokeWidth =   (height * 5)/100f
        innerLength =   (height * 50)/100f
        changesize =   (height * 40)/100f
        dynamicLength =   (height * 50)/100f



    }

   private  fun animateSize(){

       val start =   innerLength
       val end =   changesize

        ValueAnimator.ofFloat(start ,end).apply  {

            this.duration = 100
            this.interpolator =LinearInterpolator()
            this.repeatMode = ValueAnimator.REVERSE
            this.repeatCount =1


            addUpdateListener {

                Log.i("test","data ")
                innerLength = it.animatedValue as Float
                invalidate()
            }


            start()
        }


    }

    private val paint = Paint().apply {
        this.color = Color.WHITE
        this.style = Paint.Style.STROKE
        this.isAntiAlias =true
        this.isDither =true

    }


    private val innerPaint = Paint().apply {
        this.color = Color.parseColor("#0095FD")
        this.style = Paint.Style.FILL
        this.isAntiAlias = true
        this.isDither = true

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        if(canvas == null) return


        paint.strokeWidth = strokeWidth

        canvas.drawCircle(width/2f ,
            height/2f ,
            sizeLength/2f ,
            paint)


        canvas.drawCircle(width/2f ,
            height/2f ,
            innerLength/2f ,
            innerPaint)

    }

    interface onButtonClickListener{

        fun onClick()

    }

    private lateinit var onClicklistener :onButtonClickListener

    fun setOnClickListener(onClickListner: onButtonClickListener){

        this.onClicklistener = onClickListner

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if(event==null) return false


        when(event.action)
        {

            MotionEvent.ACTION_DOWN ->{


                animateSize()

            }

            MotionEvent.ACTION_UP ->{

                this.onClicklistener.onClick()

            }


        }


        return true
    }



}