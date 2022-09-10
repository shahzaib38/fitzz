package imagetrack.app.trackobject.customview

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import imagetrack.app.trackobject.R
import kotlin.math.min

class ZoomView @JvmOverloads constructor(context: Context ,
                                         attr:AttributeSet?=null,
                                          defStyle : Int =0
                                         ):  View(context,attr,){


    //Outer Color
    private var _OUTER_CIRCLE = Color.WHITE



    //DYNAMIC Color
    private var _DYNAMIC_COLOR = Color.parseColor("#0095FD")

    private var  dynamicColor @ColorInt get() = _DYNAMIC_COLOR
        set( @ColorInt value) {
            _OUTER_CIRCLE =value
            invalidate() }



    //Inner Color
    private var _INNER_CIRCLE =Color.WHITE


    //CircleStroke
    private var _CIRCLE_STROKE = d2p(DEFAULT_CIRCLE_STROKE)



    init {


        obtainAttributeStyle(context ,attr ,defStyle)

    }



    private var circleStroke  @Dimension get()= _CIRCLE_STROKE
        set(@Dimension value) {
            _CIRCLE_STROKE = value
            invalidate()
        }


    private var innerColor @ColorInt get() = _INNER_CIRCLE
        set(@ColorInt value) {
            _INNER_CIRCLE =value
            invalidate() }

    private var  outerColor @ColorInt get() = _OUTER_CIRCLE
        set(@ColorInt value) {
            _OUTER_CIRCLE = value
            invalidate() }


    private var outerPaint = Paint().apply {
        this.isAntiAlias =true
        this.color = Color.WHITE          //outerColor
        this.style = Paint.Style.STROKE
        this.strokeWidth = circleStroke

    }



    private var dynamicPaint = Paint().apply {
        this.isAntiAlias =true
        this.style = Paint.Style.STROKE
        this.strokeWidth = circleStroke
    }





    private fun obtainAttributeStyle(context: Context, attr: AttributeSet?, defStyle: Int) {

        val typedArray = context.obtainStyledAttributes(attr , R.styleable.Zoom_view ,defStyle ,0)

        try  {

        outerColor  =   typedArray.getColor(R.styleable.Zoom_view_outerColor ,outerColor)
         innerColor = typedArray.getColor(R.styleable.Zoom_view_innerColor ,innerColor )

            circleStroke = typedArray.getDimension(R.styleable.Zoom_view_circleStroke ,  circleStroke)
        //    dynamicColor = typedArray.getColor(R.styleable.Zoom_view_movableColor , dynamicColor )

        }  catch (e:Exception){

            e.stackTrace

        }finally {

            typedArray.recycle()


        }

    }


    private var outerRadius :Float =0f
    private var innerRadius :Float =0f


    private var dynamicSize :Float = 0f
    private var _dynamicRadius = THIRTY_PERCENT
    private var dynamicRadius  get() =_dynamicRadius
    set(value) {
        _dynamicRadius  = value
        invalidate() }





    override fun onSizeChanged(width: Int, height : Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(width , height, oldw, oldh)
        val smallOneSize  = (min(width ,height))/2.0

        outerRadius = smallOneSize.toFloat() * EIGHTY_PERCENT
        innerRadius = smallOneSize.toFloat() * THIRTY_PERCENT
        dynamicSize =  smallOneSize.toFloat()


    }

    private var mLastx = THIRTY_PERCENT

     fun setDynamicRadius(radius :Float?){

         if(radius==null)return

         val animate  = radius * EIGHTY_PERCENT
     val valueAnimator =    ValueAnimator.ofFloat(mLastx ,animate).apply {
             duration  = 200
             addUpdateListener {
                 dynamicRadius = it.animatedValue as Float
                  mLastx = dynamicRadius
                 invalidate()
             }

             start()
         }

         valueAnimator.addListener(object  : Animator.AnimatorListener{
             override fun onAnimationStart(animation: Animator?) {
                  animate(0,1)

             }

             override fun onAnimationEnd(animation: Animator?) {
                animate(1,0)
             }

             override fun onAnimationCancel(animation: Animator?) {

             }

             override fun onAnimationRepeat(animation: Animator?) {
             }


         })




     }


    private var show :  Int  = 0



    private fun animate(from :Int ,to :Int ){
        ValueAnimator.ofInt(from ,to).apply {
            duration =200

            addUpdateListener {
                show = it.animatedValue as Int
                invalidate()
            }
            start()
        } }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(canvas ==null)return


        createOuterCircle(canvas )
        createInnerCircle(canvas)

        createDynamicCircle(canvas)
        
    }

    private fun createDynamicCircle(canvas : Canvas ){

        dynamicPaint.color = dynamicColor
        dynamicPaint.alpha  = 255 * show

        canvas.drawCircle((width/2).toFloat() ,
            (height/2).toFloat() ,
           dynamicSize.toFloat() * dynamicRadius ,
            dynamicPaint)


    }
    
    private fun createInnerCircle(canvas : Canvas){

        outerPaint.color = innerColor
        outerPaint.alpha  = 255 * show

        canvas.drawCircle((width/2).toFloat() ,
            (height/2).toFloat() ,
            innerRadius ,
            outerPaint)
        
    }

    private fun createOuterCircle(canvas: Canvas){

        outerPaint.color = outerColor
        outerPaint.alpha  = 255 * show

        canvas.drawCircle((width/2).toFloat() ,
            (height/2).toFloat() ,
            outerRadius ,
            outerPaint)

    }


    private fun d2p(dp : Float):Float = context.resources.displayMetrics.density * dp


    companion object {

        private const val  DEFAULT_CIRCLE_STROKE = 1.2f
        private const val EIGHTY_PERCENT = 0.9f
        private const val THIRTY_PERCENT = 0.3f

    }

}