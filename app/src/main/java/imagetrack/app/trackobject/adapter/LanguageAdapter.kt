package imagetrack.app.trackobject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import imagetrack.app.lanuguages.LanguageSupportModel
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.LanguageViewDataBinding
import imagetrack.app.trackobject.viewmodel.LanguageListViewModel
import imagetrack.app.trackobject.viewmodel.MainViewModel
import imagetrack.app.translate.TranslateUtils



class LanguageAdapter(private val mViewModel : LanguageListViewModel, private val onClickListener : OnItemClickListener<String>)   : BaseAdapter<LanguageSupportModel , LanguageViewDataBinding>(diffCallback) {


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<LanguageSupportModel>() {
            override fun areItemsTheSame(
                oldItem: LanguageSupportModel,
                newItem: LanguageSupportModel
            ): Boolean =
                false

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(
                oldItem: LanguageSupportModel,
                newItem: LanguageSupportModel
            ): Boolean =
                oldItem.equals(newItem)
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): LanguageViewDataBinding {
        return           DataBindingUtil.inflate(inflater, R.layout.language_view, parent, false)

    }


    override fun bind(binding: LanguageViewDataBinding, item: LanguageSupportModel) {

        binding.apply {
            language = item
            languageId?.setOnClickListener {

             val key =   item.languageKey
            if(key!=null){
                onClickListener.clickItem(key)

            }


        }
    }
    }

    override fun onDataChanged(values: Boolean) {

    }

}