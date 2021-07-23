package imagetrack.app.trackobject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.databinding.HistoryItemDataBinding
import imagetrack.app.trackobject.ext.lessEqualTo
import imagetrack.app.trackobject.ui.activities.HistoryListener

val diffCallback = object : DiffUtil.ItemCallback<HistoryBean>() {
    override fun areItemsTheSame(oldItem: HistoryBean, newItem: HistoryBean): Boolean =
        false

    /**
     * Note that in kotlin, == checking on data classes compares all contents, but in Java,
     * typically you'll implement Object#equals, and use it to compare object contents.
     */
    override fun areContentsTheSame(oldItem: HistoryBean, newItem: HistoryBean): Boolean =
        oldItem.equals(newItem)
}


class HistoryAdapter  constructor(private val historyListener : HistoryListener)  : BaseAdapter<HistoryBean , HistoryItemDataBinding>(diffCallback) {




    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): HistoryItemDataBinding = DataBindingUtil.inflate(inflater, R.layout.history_item, parent, false)


    override fun bind(binding: HistoryItemDataBinding, item: HistoryBean) {
        binding.apply {
            historyBean = item
            shareId.setOnClickListener {
                historyListener.share(this.historyId.text.toString())
            }

            translateId.setOnClickListener {
                historyListener.translate(this.historyId.text.toString())
            }

        }

    }




    override fun onDataChanged(values: Boolean) {
      val dataSize = item?.size ?: return
        if(values){
            historyListener.show()

        }else{
            historyListener.hide()

        }
    }


    fun clearAll(){
        item?.clear()
        onDataChanged(false)
    }

}