package imagetrack.app.trackobject.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.adapter.LanguageAdapter
import imagetrack.app.trackobject.databinding.LanguageListDataBinding
import imagetrack.app.trackobject.ext.fragmentRecycle
import imagetrack.app.trackobject.navigator.LanguageListNavigator
import imagetrack.app.trackobject.viewmodel.LanguageListViewModel
import imagetrack.app.translate.TranslateUtils
import imagetrack.app.utils.LanguageArray


@AndroidEntryPoint
class LanguageListDialogFragment : BaseDialogFragment<LanguageListViewModel, LanguageListDataBinding>() ,OnItemClickListener<String> ,LanguageListNavigator  {

    private  var bind : LanguageListDataBinding?=null
    private val mViewModel by viewModels<LanguageListViewModel>()
    private  var resultText :Any?=null

    companion object{

        private const val TAG :String= "LanguageListDialogFragment"
        private const val KEY_VALUE ="textvalue"

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
        val  languageAdapter= LanguageAdapter(mViewModel, this)
        languageAdapter.setData(LanguageArray.arrayValues())

        bind?.languagelistId?.fragmentRecycle(requireContext(),languageAdapter)

    }

    override fun showDialog(fragment: FragmentManager) {
        super.show(fragment, TAG) }


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
        val postParameters: MutableMap<String, String> = HashMap()
        postParameters["q"] = resultText as String
        postParameters["target"] =item
        postParameters["key"] = TranslateUtils.SCANNER_KEY

        startProgress()

        mViewModel.getUsers(postParameters).observe(this,userObserver)
    }


  private   val userObserver = Observer<String?> { it ->
        stopProgress()
        if (it != null) {
            ScanDialogFragment.getInstance(it).showDialog(parentFragmentManager)
            dismissDialog()
        }
    }

    override fun close() {
        this.dismiss() }



}
