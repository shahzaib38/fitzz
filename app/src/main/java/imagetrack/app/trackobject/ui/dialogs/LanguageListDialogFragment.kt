package imagetrack.app.trackobject.ui.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.adapter.LanguageAdapter
import imagetrack.app.trackobject.databinding.LanguageListDataBinding
import imagetrack.app.trackobject.viewmodel.MainViewModel
import imagetrack.app.translate.TranslateUtils
import imagetrack.app.utils.LanguageArray


@AndroidEntryPoint
class LanguageListDialogFragment : BaseDialogFragment<MainViewModel, LanguageListDataBinding>() ,OnItemClickListener<String> {

    private  var bind : LanguageListDataBinding?=null
    private val mViewModel by viewModels<MainViewModel>()


  private  var languageAdapter : LanguageAdapter?=null
  private  var resultText :Any?=null
    companion object{

        private const val TAG :String= "LanguageListDialogFragment"
        const val KEY_VALUE ="textvalue"


        fun getInstance(text :String ): LanguageListDialogFragment {
            val fragmentDialog = LanguageListDialogFragment()

            val bundle = Bundle()
            bundle.putString(KEY_VALUE, text)

            fragmentDialog.arguments = bundle
            return fragmentDialog
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bind= getViewDataBinding()

        resultText = arguments?.run {
            get(ScanDialogFragment.KEY_VALUE) }

        languageAdapter= LanguageAdapter(mViewModel ,this)

setUpRecyclerView()

        bind?.closeId?.setOnClickListener {

            dismissDialog()
        }
    }


private    fun setUpRecyclerView(){

       languageAdapter?.setData(LanguageArray.arrayValues())

        bind?.languagelistId?.apply {

            this.layoutManager =    LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = languageAdapter


        }
    }





    override fun showDialog(fragment: FragmentManager) {
        super.show(fragment, TAG) }


    override fun getBindingVariable(): Int {
        return  BR.viewModel
    }

    override fun getViewModel(): MainViewModel? =mViewModel

    override fun getLayoutId(): Int {
        return  R.layout.language_list_dialog    }

    fun dismissDialog() {
        super.dismissDialog(TAG)

    }
     fun startProgress() {
         bind?.progressId?.visibility=View.VISIBLE
    }

     fun stopProgress() {
         bind?.progressId?.visibility=View.GONE
    }

    fun toast(value :String ){
        Toast.makeText(requireContext() ,value , Toast.LENGTH_LONG).show()

    }
    override fun clickItem(item: String) {
        val postParameters: MutableMap<String, String> = HashMap()
        postParameters["q"] = resultText as String
        postParameters["target"] =item
        postParameters["key"] = TranslateUtils.SCANNER_KEY


        startProgress()
        mViewModel.getUsers(postParameters).observe(this, Observer {
       stopProgress()
            if(it!=null){
                ScanDialogFragment.getInstance(it).showDialog(parentFragmentManager)
            dismissDialog()
            }

        })
    }


}
