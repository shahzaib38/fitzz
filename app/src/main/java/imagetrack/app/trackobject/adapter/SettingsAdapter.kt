package imagetrack.app.trackobject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.*
import imagetrack.app.trackobject.model.SettingBean


val diffSettingCallback = object : DiffUtil.ItemCallback<SettingBean>() {
    override fun areItemsTheSame(oldItem: SettingBean, newItem: SettingBean): Boolean =
        false

    /**
     * Note that in kotlin, == checking on data classes compares all contents, but in Java,
     * typically you'll implement Object#equals, and use it to compare object contents.
     */
    override fun areContentsTheSame(oldItem: SettingBean, newItem: SettingBean): Boolean =
        oldItem.equals(newItem)
}



class SettingsAdapter(private val onClickListener : OnItemClickListener<String>) : BaseAdapter<SettingBean, SettingItemDataBinding>(diffSettingCallback) {

    companion object{
        const val TEXT_ID =1
        const val SWITCH_ID=2 }

    override fun onDataChanged(values: Boolean) {}

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): SettingItemDataBinding {

        val binding : SettingItemDataBinding =        DataBindingUtil.inflate(inflater, R.layout.setting_item, parent, false)

        binding.root.setOnClickListener {
           val settingitem = binding.settingItem
           val data = settingitem?.data


            if(data!=null) {
                onClickListener.clickItem(data)
            }
        }


        return binding
    }

    override fun bind(binding: SettingItemDataBinding, item: SettingBean) {



        binding.apply {
            settingItem = item }


    }

}