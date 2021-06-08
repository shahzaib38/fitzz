package imagetrack.app.trackobject.recyclerview

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDecorator : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {


        outRect.top =48
    }
}