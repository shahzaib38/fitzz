package imagetrack.app.trackobject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.AccessibilityItemDataBinding
import imagetrack.app.trackobject.model.AccessibilityItem


class AccessibilityServiceAdapter(private val onClickListener : OnItemClickListener<AccessibilityItem>) : BaseAdapter<AccessibilityItem ,AccessibilityItemDataBinding>(
    diffCallback) {


    companion object {

        val diffCallback = object : DiffUtil.ItemCallback<AccessibilityItem>() {
            override fun areItemsTheSame(oldItem: AccessibilityItem, newItem: AccessibilityItem):
                    Boolean = oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(
                oldItem: AccessibilityItem,
                newItem: AccessibilityItem
            ): Boolean =
                oldItem == newItem
        }

    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): AccessibilityItemDataBinding {
        val binding : AccessibilityItemDataBinding =  DataBindingUtil.inflate(inflater, R.layout.accessibility_layout  , parent, false)
        binding.root.setOnClickListener {
          var accessibilityItem =  binding.accessibilityItem
            if(accessibilityItem!=null) {
                onClickListener.clickItem(accessibilityItem) }}

        return binding }

    override fun bind(binding: AccessibilityItemDataBinding, item: AccessibilityItem) {

        binding.let {
            binding.accessibilityItem = item }

    }

    override fun onDataChanged(values: Boolean) {



    }


}