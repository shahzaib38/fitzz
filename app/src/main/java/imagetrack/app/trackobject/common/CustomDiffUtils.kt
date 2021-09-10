package imagetrack.app.trackobject.common

import androidx.recyclerview.widget.DiffUtil
import imagetrack.app.utils.Model



class CustomDiffUtils<M : Model>(
    private val newList: ArrayList<M>?,
    private val oldList: ArrayList<M>) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {

        return newList?.size ?: 0 }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return newList?.get(newItemPosition)?.id == oldList?.get(oldItemPosition)?.id }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        val result =  newList?.get(newItemPosition)?.compareTo(oldList!![oldItemPosition])

        return result == 0 }


}