package imagetrack.app.trackobject.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.PdfOpenDataBinding
//import imagetrack.app.trackobject.ext.ads
import imagetrack.app.trackobject.ext.launchActivity
import imagetrack.app.trackobject.navigator.PdfNavigator
import imagetrack.app.trackobject.ui.activities.MainActivity
import imagetrack.app.trackobject.ui.activities.PdfActivity
import imagetrack.app.trackobject.viewmodel.PdfViewModel


@AndroidEntryPoint
class OpenPdfDialog : BaseDialogFragment<PdfViewModel, PdfOpenDataBinding>()  ,PdfNavigator {


    private val mViewModel by viewModels<PdfViewModel>()
    private var mBinding  : PdfOpenDataBinding? =null
    private var   argument :Any?=null
    private var mMainActivity : MainActivity? =null

    override fun getBindingVariable(): Int =BR.viewModel
    override fun getViewModel(): PdfViewModel? =mViewModel
    override fun getLayoutId(): Int= R.layout.open_dialog_layout


    companion object{

        private const val TAG : String="PdfOpenDialog"

        @VisibleForTesting
        const val KEY_VALUE ="textvalue"

        fun getInstance(text: String): OpenPdfDialog {
            val fragmentDialog = OpenPdfDialog()
            val bundle = Bundle()
            bundle.putString(KEY_VALUE, text)
            fragmentDialog.arguments = bundle
            return fragmentDialog
        }
    }


    fun showDialog(fragmentManager: FragmentManager) {
        super.showDialogs(fragmentManager ,TAG)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = getViewDataBinding()

        mViewModel.setNavigator(this)
        dialog?.setCanceledOnTouchOutside(false)




         val baseActivitty  = getBaseActivity()

         if(baseActivitty is MainActivity){
            mMainActivity =baseActivitty }

        argument = arguments?.run {
            this.get(PdfCreatorDialog.KEY_VALUE) } as String

//        setupAds()

    }
//
//
//    private fun  setupAds(){
//        mBinding?.adsInclude?.apply {
//            val unitId=    resources.getString(R.string.pdf_open)
//            //adsId.ads(requireContext() ,unitId,advertiseId)
//            lifecycleScope.launch(Dispatchers.IO) {
//                adsId.ads(requireContext(), unitId, advertiseId)
//            }
//        }
//
//    }










    override fun generatePdf() {
        dismiss()
        mMainActivity?.launchActivity(PdfActivity::class.java)
    }





    override fun close(){
    }

    override fun openInternetDialog() {
    }

}