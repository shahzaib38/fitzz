package imagetrack.app.trackobject.ui.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.pdf.PDFMetaData
import imagetrack.app.pdf.PDFUtil
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.PdfDataBinding
//import imagetrack.app.trackobject.ext.ads
import imagetrack.app.trackobject.ext.showOpenDialog
import imagetrack.app.trackobject.navigator.PdfNavigator
import imagetrack.app.trackobject.ui.activities.MainActivity
import imagetrack.app.trackobject.viewmodel.PdfViewModel

@AndroidEntryPoint
class PdfCreatorDialog : BaseDialogFragment<PdfViewModel, PdfDataBinding>()  , PdfNavigator  ,PDFUtil.PDFUtilListener{


    private val mViewModel by viewModels<PdfViewModel>()
    private var mBinding  : PdfDataBinding? =null
    private var   argument :Any?=null
    private var mMainActivity : MainActivity? =null

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModel(): PdfViewModel = mViewModel

    override fun getLayoutId(): Int  = R.layout.pdf_dialog


    private fun progressStart(){
        mBinding?.pdfProgress?.visibility =View.VISIBLE }

    private  fun progressStop(){
        mBinding?.pdfProgress?.visibility =View.GONE }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = getViewDataBinding()

        mViewModel.setNavigator(this)
        dialog?.setCanceledOnTouchOutside(false)


        val baseActivitty  = getBaseActivity()

        if(baseActivitty is MainActivity){
            mMainActivity =baseActivitty }

        argument = arguments?.run {
            get(KEY_VALUE) } as String

    }



  private   fun generatePdf(value :String){



      val text=    mBinding?.includePdf?.pdfEditText?.text.toString()
      val header = mBinding?.includePdf?.headerEditText?.text.toString()

      val pdMetadata = createMetaData(text ,header, value)

        if(pdMetadata!=null ){

            PDFUtil.getInstance(requireActivity()).generatePDF(pdMetadata, this)

        } else {

            toast("File Name must not be empty")
        }


  }

    companion object{

        private const val TAG : String="PdfCreatorDialog"

        @VisibleForTesting
        const val KEY_VALUE ="textvalue"

        fun getInstance(text : String): PdfCreatorDialog {
            val fragmentDialog = PdfCreatorDialog()
            val bundle = Bundle()
            bundle.putString(KEY_VALUE, text)
            fragmentDialog.arguments = bundle
            return fragmentDialog
        }
    }

   private   fun dismissDialog() {

        dismissDialog(TAG)
    }


     fun showDialog(fragmentManager: FragmentManager) {
        super.showDialogs(fragmentManager ,TAG)
    }

   private  fun toast(value : String){

        Toast.makeText(requireContext() , value ,Toast.LENGTH_LONG).show()
    }
    override fun pdfGenerationSuccess() {

        progressStop()
        dismissDialog()

        mMainActivity?.showOpenDialog()

        toast("Pdf Created")

    }

    override fun pdfGenerationFailure(exception: Exception?) {
        progressStop()
        toast("Pdf Fail Try Again  ${exception?.message}")
        dismissDialog()

    }

    override fun generatePdf() {
        progressStart()
        val value = argument as String
        generatePdf(value)
       }

    override fun close() {
        dismissDialog() }

    override fun openInternetDialog() {

    }



    private fun createMetaData(text :String ,header :String  ,data :String ) : PDFMetaData?{

        val pdfMetaDataBuilder = PDFMetaData.Builder()
        pdfMetaDataBuilder.setData(data.trim())

        if(text.isEmpty()){
            return null
        }else{

            pdfMetaDataBuilder.setFileName(text.trim()) }

        if(header.isEmpty()){
            pdfMetaDataBuilder.setHeader("")

        }else{
            pdfMetaDataBuilder.setHeader(header) }

        return pdfMetaDataBuilder.builder()
    }




}