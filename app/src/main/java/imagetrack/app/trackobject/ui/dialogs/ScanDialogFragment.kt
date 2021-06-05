package imagetrack.app.trackobject.ui.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ClipBoardManager
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.ads.InterstitialAds
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.databinding.ScanDialogDataBinding
import imagetrack.app.trackobject.ext.load
import imagetrack.app.trackobject.navigator.ScanDialogNavigator
import imagetrack.app.trackobject.viewmodel.ScanDialogViewModel
import java.util.*


@AndroidEntryPoint
 class ScanDialogFragment : BaseDialogFragment<ScanDialogViewModel, ScanDialogDataBinding>()  , ScanDialogNavigator , DialogInterface.OnDismissListener {


    override fun onDismiss(dialog: DialogInterface) {}

    private val mViewModel by viewModels<ScanDialogViewModel>()
    private var mBinding  :ScanDialogDataBinding? =null


    override fun getBindingVariable(): Int =BR.viewModel

    override fun getViewModel(): ScanDialogViewModel = mViewModel

    override fun getLayoutId(): Int  = R.layout.translated_text

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = getViewDataBinding()

        mViewModel.setNavigator(this)
        dialog?.setCanceledOnTouchOutside(false);

        val arguments = arguments?.run {
            get(KEY_VALUE) }

        mBinding?.apply {
            translatedtext.setText(arguments as String)
        }?:throw NullPointerException("Scan Dialog Fragment   Data Binding is null")

        InterstitialAds.load(requireContext())
        mBinding?.adView?.load()
        isCancelable =false


    }


        companion object{
        private const val TAG : String="ScanDialogFragment"

            @VisibleForTesting
             const val KEY_VALUE ="textvalue"

        fun getInstance(text: String): ScanDialogFragment {
            val fragmentDialog = ScanDialogFragment()
            val bundle = Bundle()
            bundle.putString(KEY_VALUE, text)

            fragmentDialog.arguments = bundle
            return fragmentDialog
            }
    }


    override fun dismissDialog() {}

    override fun copy() {
        ClipBoardManager.clipInstance(requireContext()).copy(getText())
               toast("Copied") }

    override fun edit() {}

    private fun getText():String{
        return mBinding?.translatedtext?.text?.toString() ?: "Try again" }


  private fun  toast(value: String){
       Toast.makeText(requireContext(), value, Toast.LENGTH_LONG).show() }

    override fun share() {
        toast("Sharing")
        super.shareData(getText())
    }

        override fun pdf() {
        PdfCreatorDialog.getInstance(getText()).showDialog(childFragmentManager) }



    override fun translate() {
                LanguageListDialogFragment.getInstance(
                    getText()
                ).showDialog(parentFragmentManager)
                dismiss()
    }

    override fun startProgress() {}

    override fun stopProgress() {}

    override fun exit() {
        InterstitialAds.show(requireActivity())
        saveToDataBase()
            this.dismiss()

    }

  private fun  saveToDataBase(){

        try{
       mViewModel.insertHistory(HistoryBean(value =getText(),date = getDateTime().time.toString()))
        } catch (e: Exception){

            val mess =e.message
            if(mess!=null) {
                toast(mess)
            }

            val isShowing =dialog?.isShowing
            if(isShowing!=null && isShowing ) {
             toast("Showing ")
                this.dismiss()
            }else{
                toast("Not Showing")
            }

        }
    }

    private fun getDateTime(): Date {
        return  Calendar.getInstance().time }

}