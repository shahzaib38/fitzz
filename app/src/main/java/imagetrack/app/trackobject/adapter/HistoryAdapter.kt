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
    override fun areItemsTheSame(oldItem: HistoryBean, newItem: HistoryBean):
            Boolean = oldItem.id == newItem.id

    /**
     * Note that in kotlin, == checking on data classes compares all contents, but in Java,
     * typically you'll implement Object#equals, and use it to compare object contents.
     */
    override fun areContentsTheSame(oldItem: HistoryBean, newItem: HistoryBean): Boolean =
        oldItem == newItem
}


class HistoryAdapter  constructor(private val historyListener : HistoryListener)  : BaseAdapter<HistoryBean , HistoryItemDataBinding>(diffCallback) {




    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): HistoryItemDataBinding {

        val binding : HistoryItemDataBinding =  DataBindingUtil.inflate(inflater, R.layout.history_item, parent, false)
        binding.run {

            shareId.setOnClickListener {

             val text =   binding.historyId.text.toString()
                historyListener.share(text) }

            translateId.setOnClickListener {
                val text =   binding.historyId.text.toString()
                historyListener.translate(text) }
        }


        return binding
    }


    override fun bind(binding: HistoryItemDataBinding, item: HistoryBean) {
        binding.apply {
            historyBean = item } }




    override fun onDataChanged(values: Boolean) {
        if(values){
            historyListener.show()
        }else{
            historyListener.hide() } }


    fun clearAll(){
        item?.clear()
        onDataChanged(false) }

}