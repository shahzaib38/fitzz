package imagetrack.app.trackobject.ui.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.pdf.PDFUtil
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.adapter.PdfAdapter
import imagetrack.app.trackobject.databinding.PdfViewDataBinding
import imagetrack.app.trackobject.ext.recycle
//import imagetrack.app.trackobject.ext.ads
import imagetrack.app.trackobject.ext.tryOpenPathIntent
import imagetrack.app.trackobject.helpers.APPLICATION_ID
import imagetrack.app.trackobject.helpers.OPEN_AS_PdF
import imagetrack.app.trackobject.storage.Storage
import imagetrack.app.trackobject.viewmodel.PdfViewModel
import imagetrack.app.utils.CameraPermissions
import java.io.File

interface PDFListener {

    fun hide()
    fun show()
    fun sharePDF(file : File)
     fun openPdf(file: File)
}



@AndroidEntryPoint
class PdfActivity : BaseActivity<PdfViewModel, PdfViewDataBinding>() ,PDFListener {

    private val mViewModel by viewModels<PdfViewModel>()
    private var mPdfDataBinding : PdfViewDataBinding?=null

    private var mstorage = Storage(this)

    override fun getBindingVariable(): Int  = BR.viewModel

    override fun getLayoutId(): Int = R.layout.pdf_layout

    override fun getViewModel(): PdfViewModel = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mPdfDataBinding = getViewDataBinding()

//        setupAds()
        openGallery()


    }
    private fun requestGalleryPermission(){
        requestPermissions(CameraPermissions.GALLERY_ARRAY, CameraPermissions.CAMERA_GALLERY_PERMISSION)
    }
   private  fun openGallery(){
       if (CameraPermissions.isGalleryPermissionGranted(this))
       {
           loadPdfFiles()

       } else {
           requestGalleryPermission()

       }



   }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode ==  CameraPermissions.CAMERA_GALLERY_PERMISSION){
            loadPdfFiles() }else{
            requestGalleryPermission()

        }
    }

//    private fun  setupAds(){
//
//        mPdfDataBinding?.adsInclude?.apply {
//
//            val unitId=    resources.getString(R.string.all_pdf)
//           // adsId.ads(this@PdfActivity ,unitId,advertiseId)
//            lifecycleScope.launch(Dispatchers.IO) {
//                adsId.ads(this@PdfActivity, unitId, advertiseId)
//            }
//        }
//
//    }


    private fun loadPdfFiles(){

        val pdfAdapter =PdfAdapter(this)

        try {

            val pdfFiles = mstorage.getFiles(PDFUtil.directory.path)

             pdfAdapter.setData(ArrayList(pdfFiles))
            mPdfDataBinding?.pdfRecyclerview?.recycle(this ,pdfAdapter)


        }catch (e : Exception ){

            hide()


        }


    }







    override fun hide() {
        mPdfDataBinding?.let {
            it.pdfRecyclerview.visibility = View.GONE
            it.emptyId.visibility= View.VISIBLE
        }
    }




    override fun show() {

        mPdfDataBinding?.let {

            it.pdfRecyclerview.visibility = View.VISIBLE
            it.emptyId.visibility= View.GONE

        }
    }

    override fun sharePDF(file: File) {
        val pdfUri = FileProvider.getUriForFile(this, "$APPLICATION_ID.provider", file)

        super.sharePdfFile(pdfUri)
    }
    override fun openPdf(file: File) {
        tryOpenPathIntent(file.path,OPEN_AS_PdF)

    }




}