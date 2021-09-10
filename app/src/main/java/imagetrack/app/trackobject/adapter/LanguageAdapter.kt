package imagetrack.app.trackobject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import imagetrack.app.lanuguages.LanguageSupportModel
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.LanguageViewDataBinding
import imagetrack.app.trackobject.viewmodel.LanguageListViewModel


class LanguageAdapter(
    private val context: Context,
    private val mViewModel: LanguageListViewModel,
    private val onClickListener: OnItemClickListener<String>,
    private val lifecycleScope: LifecycleCoroutineScope
) : MultiViewModelBaseAdapter<LanguageSupportModel, LanguageViewDataBinding>(diffCallback) {





    companion object {


        val diffCallback = object : DiffUtil.ItemCallback<LanguageSupportModel>() {
            override fun areItemsTheSame(
                oldItem: LanguageSupportModel,
                newItem: LanguageSupportModel
            ): Boolean =oldItem.id == newItem.id



            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(
                oldItem: LanguageSupportModel,
                newItem: LanguageSupportModel
            ): Boolean = oldItem == newItem


        }
    }




    override fun createBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup): LanguageViewDataBinding {

       val binding : LanguageViewDataBinding=        DataBindingUtil.inflate(inflater, R.layout.language_view, parent, false)

        binding?.root?.setOnClickListener {

                        val key = binding?.language?.languageKey
                        if (key != null) {
                            onClickListener.clickItem(key) } }

        return binding
    }




    override fun onDataChanged(values: Boolean) {

    }

    override fun bind(binding: LanguageViewDataBinding, item: LanguageSupportModel, position: Int) {

        binding.apply {
            language = item }


    }

}