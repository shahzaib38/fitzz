package imagetrack.app.trackobject.ui.dialogs

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ClipBoardManager
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.ads.InterstitialAds
import imagetrack.app.trackobject.ads.NativAdAdvance
import imagetrack.app.trackobject.ads.OnAdVisibilityListener
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.databinding.ScanDialogDataBinding
import imagetrack.app.trackobject.navigator.ScanDialogNavigator
import imagetrack.app.trackobject.ui.activities.EditorActivity
import imagetrack.app.trackobject.viewmodel.ScanDialogViewModel
import java.util.*


@AndroidEntryPoint
 class ScanDialogFragment : BaseDialogFragment<ScanDialogViewModel, ScanDialogDataBinding>()  , ScanDialogNavigator , DialogInterface.OnDismissListener  ,OnAdVisibilityListener{


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

            val derivedText =arguments as String

            if(derivedText.isNotEmpty()){

                translatedtext.text = derivedText

            }else {

                translatedtext.text = NO_TEXT_FOUND
            }

        }?:throw NullPointerException("Scan Dialog Fragment   Data Binding is null")

        InterstitialAds.load(requireContext(),resources.getString(R.string.full_screen_exit))
      //  mBinding?.adView?.load()
        isCancelable =false


        mBinding?.let {
            NativAdAdvance.loadAd(requireActivity(), it ,this ,resources.getString(R.string.scan_editor_unitId)) }
    }


        companion object{
        private const val TAG : String="ScanDialogFragment"
        private const val NO_TEXT_FOUND ="No Text found Try Again"

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

    override fun edit() {

        val intent = Intent(context ,EditorActivity::class.java)
        intent.putExtra(resources.getString(R.string.edit_value),getText())
        startActivity(intent)
       this.dismiss()
    }

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
            this.dismiss()

    }

//    saveToDataBase()



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


    override fun showAd() {
        mBinding?.linearLayout2?.visibility =View.VISIBLE }

    override fun hideAd(){
        mBinding?.linearLayout2?.visibility =View.GONE }

}