package imagetrack.app.trackobject.ui.dialogs

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
//import com.google.android.ads.nativetemplates.CustomTemplateView
//import com.google.android.ads.nativetemplates.TemplateView
//import com.google.android.gms.ads.*
//import com.google.android.gms.ads.nativead.NativeAd
//import com.google.android.gms.ads.nativead.NativeAdOptions
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.lanuguages.LanguageSupportModel
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.adapter.LanguageAdapter
import imagetrack.app.trackobject.databinding.LanguageListDataBinding
import imagetrack.app.trackobject.ext.fragmentRecycle
import imagetrack.app.trackobject.navigator.LanguageListNavigator
import imagetrack.app.trackobject.viewmodel.LanguageListViewModel
import imagetrack.app.translate.TranslateUtils
import imagetrack.app.utils.InternetConnection
import imagetrack.app.utils.LanguageArray


@AndroidEntryPoint
class LanguageListDialogFragment : BaseDialogFragment<LanguageListViewModel, LanguageListDataBinding>() ,OnItemClickListener<String> ,LanguageListNavigator  {

    private  var bind : LanguageListDataBinding?=null
    private val mViewModel by viewModels<LanguageListViewModel>()
    private  var resultText :Any?=null
//    private var adLoader: AdLoader? = null

    companion object{

        private const val TAG :String= "LanguageListDialogaa"
        private const val KEY_VALUE ="textvalue"
        const val ITEMS_PER_AD = 8


        private var mRecyclerViewItems  : ArrayList<LanguageSupportModel>  = ArrayList()

//        private val mNativeAds :ArrayList<NativeAd> =  ArrayList();

        private  var    languageAdapter : LanguageAdapter?=null


        fun getInstance(text: String): LanguageListDialogFragment {
            val fragmentDialog = LanguageListDialogFragment()
            val bundle = Bundle()
            bundle.putString(KEY_VALUE, text)
            fragmentDialog.arguments = bundle
            return fragmentDialog }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind= getViewDataBinding()

        mViewModel.setNavigator(this)
        resultText = arguments?.run { get(ScanDialogFragment.KEY_VALUE) }
          languageAdapter= LanguageAdapter(requireContext(), mViewModel, this, lifecycleScope)

        mRecyclerViewItems.addAll(LanguageArray.arrayValues())


            languageAdapter?.submitList(mRecyclerViewItems)

        bind?.languagelistId?.fragmentRecycle(requireContext(), languageAdapter!!)

    }

    fun showDialog(fragment: FragmentManager) {
        super.showDialogs(fragment, TAG)
     }


    override fun getBindingVariable(): Int {
        return  BR.viewModel
    }

    override fun getViewModel(): LanguageListViewModel? =mViewModel

    override fun getLayoutId(): Int {
        return  R.layout.language_list_dialog    }

   private fun dismissDialog() {
        super.dismissDialog(TAG) }

     private fun startProgress() {
         bind?.progressId?.visibility=View.VISIBLE }

    private   fun stopProgress() {
         bind?.progressId?.visibility=View.GONE }


    override fun clickItem(item: String) {
        if(!InternetConnection.isInternetAvailable(requireActivity()))
        {
            InternetConnectionDialog.getInstance().showDialog(parentFragmentManager)
            return }

        val postParameters: MutableMap<String, String> = HashMap()
        postParameters["q"] = resultText as String
        postParameters["target"] =item
        postParameters["key"] = TranslateUtils.SCANNER_KEY

        startProgress()

        mViewModel.getUsers(postParameters).observe(this, userObserver)
    }


  private   val userObserver = Observer<String?> { it ->
        stopProgress()
        if (it != null) {
            dismiss()

            ScanDialogFragment.getInstance(it).showDialog(parentFragmentManager)
        }
    }

    override fun close() {
        this.dismiss() }

}
