package imagetrack.app.trackobject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.databinding.HistoryItemDataBinding

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


class HistoryAdapter  constructor(private val onClickListener : OnItemClickListener<HistoryBean>)  : BaseAdapter<HistoryBean , HistoryItemDataBinding>(diffCallback) {




    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): HistoryItemDataBinding = DataBindingUtil.inflate(inflater, R.layout.history_item, parent, false)


    override fun bind(binding: HistoryItemDataBinding, item: HistoryBean) {
        binding.apply {
            historyBean = item
            historyId?.setOnClickListener {



            }
        }
    }

    override fun onDataChanged(values: Boolean) {

    }

}