package imagetrack.app.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.google.mlkit.vision.text.Text
import java.util.concurrent.locks.ReentrantLock


class GraphicOverlay(context: Context, attribute  : AttributeSet) : androidx.appcompat.widget.AppCompatTextView(context  , attribute){

    val textBuilder  = StringBuffer(500)          //on max capacity (500 +1)*2

    private val lock = ReentrantLock()

     fun add(graphic : Text) {
        lock.lock()
        textBuilder.append(graphic.text)
        postInvalidate()
        lock.unlock()
     }


    fun clearAll(){
        lock.lock()
        textBuilder.delete(0 ,textBuilder.length)
        lock.unlock()
        postInvalidate() }



    /** Draws the overlay with its associated graphic objects.  */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        println("Thread NAem ${Thread.currentThread().name}")
        text = textBuilder

        }
    }



