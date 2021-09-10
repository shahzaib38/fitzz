package imagetrack.app.trackobject.ui.dialogs

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
//import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ClipBoardManager
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.databinding.ScanDialogDataBinding
import imagetrack.app.trackobject.ext.internetConnectionDialog
import imagetrack.app.trackobject.ext.launchActivity
import imagetrack.app.trackobject.ext.showLanguageList
import imagetrack.app.trackobject.ext.showPdf
import imagetrack.app.trackobject.navigator.ScanDialogNavigator
import imagetrack.app.trackobject.ui.activities.EditorActivity
import imagetrack.app.trackobject.ui.activities.InAppPurchaseActivity
import imagetrack.app.trackobject.ui.activities.MainActivity
import imagetrack.app.trackobject.viewmodel.ScanDialogViewModel
import imagetrack.app.utils.CameraPermissions
import imagetrack.app.utils.DateUtils
import imagetrack.app.utils.InternetConnection


@AndroidEntryPoint
 class ScanDialogFragment : BaseDialogFragment<ScanDialogViewModel, ScanDialogDataBinding>()  , ScanDialogNavigator , DialogInterface.OnDismissListener  {

    override fun onDismiss(dialog: DialogInterface) {}
    private val mViewModel by viewModels<ScanDialogViewModel>()
    private var mBinding  :ScanDialogDataBinding? =null
    private var subscriptionStatus : SubscriptionStatus?=null
    private var mMainActivity :MainActivity? =null

    override fun getBindingVariable(): Int =BR.viewModel
    override fun getViewModel(): ScanDialogViewModel = mViewModel
    override fun getLayoutId(): Int  = R.layout.translated_text

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = getViewDataBinding()
        mViewModel.setNavigator(this)
        dialog?.setCanceledOnTouchOutside(false)


        val baseActivitty  = getBaseActivity()

        if(baseActivitty is MainActivity){
            mMainActivity =baseActivitty }

        val arguments = arguments?.run { get(KEY_VALUE) }

        mBinding?.apply {
            val derivedText =arguments as? String
            this.translatedtext.translateText(derivedText) }
//
//        mBinding?.run{
//            val adRequest = AdRequest.Builder().build()
//            this.bannerId.loadAd(adRequest)
//
//        }

    //    checkTranslationAvailable()

    }

    private fun requestGalleryPermission(){
        requestPermissions(
            CameraPermissions.GALLERY_ARRAY,
            CameraPermissions.CAMERA_GALLERY_PERMISSION
        )
    }


   private  fun openGallery(){

            if (CameraPermissions.isGalleryPermissionGranted(requireContext()))
            {
                dismiss()
                mMainActivity?.showPdf(getText())
            } else
            {
                requestGalleryPermission() }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       if(requestCode == CameraPermissions.CAMERA_GALLERY_PERMISSION){
           dismiss()
           mMainActivity?.showPdf(getText())
       }
    }










    private  fun EditText.translateText(derivedText: String?){
        val isTextNotNullAndEmpty = derivedText !=null && derivedText.isNotEmpty()

        if (isTextNotNullAndEmpty) {
            setText(derivedText)
        } else {
            setText(NO_TEXT_FOUND)
        }
    }





    private fun openIntent(){

        mMainActivity?.launchActivity(InAppPurchaseActivity::class.java)

    }



    fun showDialog(fragment: FragmentManager) {
        super.showDialogs(fragment, TAG) }



    companion object{
        private const val TAG : String="ScanDialogFragment"
         const val NO_TEXT_FOUND ="No Text found Try Again"

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
               toast("Copied")
    }

    override fun edit() {
        val intent = Intent(context, EditorActivity::class.java)
        intent.putExtra(resources.getString(R.string.edit_value), getText())
        startActivity(intent)
       this.dismiss()
    }

    private fun getText():String{
        return mBinding?.translatedtext?.text?.toString() ?: "" }


  private fun  toast(value: String){
       Toast.makeText(requireContext(), value, Toast.LENGTH_LONG).show() }

    override fun share() {
        toast("Sharing")
        super.shareData(getText())
    }

        override fun pdf() {


            openGallery()


        }

//    private fun checkTranslationAvailable(){
//        mViewModel.subscriptionLiveData.observe(this , {
//            subscriptionStatus =  it
//
//        })
//    }






    override fun translate() {

        println("Translate")
//      val subscriptionStatus =  subscriptionStatus

        if(!InternetConnection.isInternetAvailable(requireActivity()))
        {

            mMainActivity?.internetConnectionDialog()


            return }

//

//
//        if(subscriptionStatus!=null){
//            val isExpire = subscriptionStatus.isExpired()
//            if(isExpire){
//                dismiss()
//                openIntent()
//            } else {



                dismiss()

                    mMainActivity?.showLanguageList(getText())

//
//
//            }
//        }
//        else {
//            openIntent() }
//

    }

    override fun startProgress(){}

    override fun stopProgress(){}

    override fun exit() {
          saveToDataBase()
        this.dismiss() }




    private fun  saveToDataBase(){

        if(getText().isEmpty() || getText().trim() == NO_TEXT_FOUND)return

        try{
       mViewModel.insertHistory(
           HistoryBean(
               value = getText().trim(),
               date = DateUtils.getTime().toString()
           )
       )
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




}