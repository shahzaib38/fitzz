package imagetrack.app.trackobject.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.Toast

class DialView  @JvmOverloads constructor(context : Context ,attr : AttributeSet?=null ,defStyleAttr :Int =0) : View(context ,attr ,defStyleAttr) {


    override fun onFinishInflate() {
        super.onFinishInflate()

        println("OnInflation FInish ")


    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)


        println("Change Layout"+changed)
        println("Left Layout "+left)
        println("Top Layout "+top)
        println("right Layout "+right)
        println("bottom  Layout "+bottom)



    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        println("size change new width "+w   + "Size change height "+h )
        println("size change old width "+oldw   + "Size change height "+oldh )

        println("View Left"+getLeft())
        println("View Right"+getRight())
        println("View Top"+getTop())
        println("View Bottom"+getBottom())



    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


        println("Measure Width "+widthMeasureSpec)
        println("Measure Height "+heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        var rect = Rect()

        var paint =Paint(Paint.ANTI_ALIAS_FLAG)

        rect.bottom =100
        rect.top =500
        rect.left =300
        rect.right =400


        canvas?.drawRect(rect ,paint)

    }


    override fun performClick(): Boolean {


   Toast.makeText(context ,"Clicked ",Toast.LENGTH_LONG).show()

        return super.performClick()
    }

}