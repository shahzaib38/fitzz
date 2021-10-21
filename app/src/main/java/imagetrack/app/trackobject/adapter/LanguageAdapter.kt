package imagetrack.app.trackobject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import imagetrack.app.lanuguages.LanguageSupportModel
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.LanguageViewDataBinding
import imagetrack.app.trackobject.databinding.NativeAdsDataBinding
import imagetrack.app.trackobject.model.Ads
import imagetrack.app.lanuguages.BaseLanguageModel


class LanguageAdapter(
    private val onClickListener: OnItemClickListener<String>,
) : MultiViewModelBaseAdapter<BaseLanguageModel, ViewDataBinding>(diffCallback) {





    companion object {
        private const val LANGUAGE_ITEMS  =1
        private const  val NATIVE_ADS  =2



        val diffCallback = object : DiffUtil.ItemCallback<BaseLanguageModel>() {
            override fun areItemsTheSame(
                oldItem: BaseLanguageModel,
                newItem: BaseLanguageModel
            ): Boolean = oldItem.ids == newItem.ids



            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(
                oldItem: BaseLanguageModel,
                newItem: BaseLanguageModel
            ): Boolean = oldItem == newItem


        }
    }


    override fun getItemViewType(position: Int): Int {

      val baseLanguageModel =  item[position]

        if (baseLanguageModel is Ads) {
            return NATIVE_ADS; }

        return LANGUAGE_ITEMS }

    override fun createBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding =
        when(viewType){

            NATIVE_ADS ->{
                val binding : NativeAdsDataBinding =        DataBindingUtil.inflate(inflater, R.layout.ads_language_layout, parent, false)

                binding }

            else ->{
                val binding : LanguageViewDataBinding=        DataBindingUtil.inflate(inflater, R.layout.language_view, parent, false)
                binding.root.setOnClickListener {
                    val key = binding.language?.languageKey
                    if (key != null) {
                        onClickListener.clickItem(key)
                    } }

               binding
            } }




    override fun onDataChanged(values: Boolean) {}

    override fun bind(binding: ViewDataBinding, item: BaseLanguageModel, position: Int) {


        when(binding){

            is NativeAdsDataBinding ->{

                if(item is Ads){
                    val   nativeAds =   item.nativeAds

                    if(nativeAds!=null){

                         binding.apply {


                                 this@apply.includeId.adsId.setNativeAd(nativeAds)



                             binding.includeId.advertiseId.visibility =View.GONE
                             binding.includeId.adsId.visibility =View.VISIBLE


                         }


                       }else{
                        binding.includeId.advertiseId.visibility =View.VISIBLE
                        binding.includeId.adsId.visibility =View.GONE

                    }




                 }


             }

            is LanguageViewDataBinding ->{
                binding.apply {
                    if(item is LanguageSupportModel)
                        language = item } }

        }



    }

    override fun getItemCount(): Int {

        return item.size
    }

}