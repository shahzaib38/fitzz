package imagetrack.app.trackobject.ui.dialogs

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ClipBoardManager
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.database.preferences.AdThreshold
import imagetrack.app.trackobject.databinding.ScanDialogDataBinding
//import imagetrack.app.trackobject.ext.showLanguageList
import imagetrack.app.trackobject.ext.showPdf
import imagetrack.app.trackobject.ext.translateText
//import imagetrack.app.trackobject.navgraph.NavGraph
import imagetrack.app.trackobject.navigator.ScanDialogNavigator
import imagetrack.app.trackobject.ui.activities.EditorActivity
import imagetrack.app.trackobject.ui.activities.MainActivity
import imagetrack.app.trackobject.ui.fragment.BaseFragment
import imagetrack.app.trackobject.viewmodel.ScanDialogViewModel
import imagetrack.app.trackobject.viewmodel.ScanViewModel
import imagetrack.app.utils.CameraPermissions
import imagetrack.app.utils.DateUtils
import imagetrack.app.utils.InternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
 class ScanDialogFragment : BaseFragment<ScanDialogViewModel, ScanDialogDataBinding>()  , ScanDialogNavigator , DialogInterface.OnDismissListener {

    override fun onDismiss(dialog: DialogInterface) {}
    private val mViewModel by viewModels<ScanDialogViewModel>()
    private var mBinding  :ScanDialogDataBinding? =null
    private var mainActivity :MainActivity?=null

   private  val args: ScanDialogFragmentArgs by navArgs()

   private var mNavController : NavController? =null

    private val activityViewModel by activityViewModels<ScanViewModel>()



    override fun getBindingVariable(): Int = BR.viewModel
    override fun getViewModel(): ScanDialogViewModel = mViewModel
    override fun getLayoutId(): Int  = R.layout.translated_text

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        mBinding = getViewDataBinding()

        mNavController = findNavController()
        println("Scan Dialog Fragment" +mNavController)

        val baseActivity =   getBaseActivity()

        if(baseActivity is MainActivity){
            mainActivity = baseActivity
        }


        mViewModel.setNavigator(this)
        //dialog?.setCanceledOnTouchOutside(false)
      //  val arguments = arguments?.run { get(KEY_VALUE) }
//           val derivedText =   args.textvalue
//
//
//        mBinding?.apply {
//            //val derivedText =arguments as? String
//            this.translatedId.translatedtext.translateText(derivedText) }


        activityViewModel.progressLiveData.observe(viewLifecycleOwner) {

            mBinding?.topSectionnId?.progressId?.isVisible =it

        }


        activityViewModel.scannerText.observe(viewLifecycleOwner) { scannedText->
            if(scannedText.isEmpty()){
                mBinding?.translatedId?.translatedtext?.setText(NO_TEXT_FOUND)
            }else {
                mBinding?.translatedId?.translatedtext?.setText(scannedText) }
        }


        setupAds()


    }

    private fun setupAds(){

     val activity = mainActivity ?: return

        mBinding?.adviewId?.run{
            val adRequest = AdRequest.Builder().build()
            this.bannerId.loadAd(adRequest)
            this.bannerId.adListener = object : AdListener(){

                override fun onAdClicked() {
                    super.onAdClicked()

                }

                override fun onAdLoaded() {
                    super.onAdLoaded()

                }

            }
        }

    }

    private fun requestGalleryPermission(){
        requestPermissions(
            CameraPermissions.GALLERY_ARRAY,
            CameraPermissions.CAMERA_GALLERY_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       if(requestCode == CameraPermissions.CAMERA_GALLERY_PERMISSION){
      //     dismiss()
      //    mainActivity?.showPdf(getText())
       }
    }
//
//    fun showDialog(fragmentManager: FragmentManager) {
//        super.showDialogs(fragmentManager ,TAG) }



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

    override fun speak() {

        //NavGraph.navigate(NavGraph.SCAN_TO_SPEAK ,this.findNavController(),getText())
    }

    override fun copy() {
        ClipBoardManager.clipInstance(requireContext()).copy(getText())
               toast("Copied")
    }

    override fun edit() {
        val intent = Intent(context, EditorActivity::class.java)
        intent.putExtra(resources.getString(R.string.edit_value), getText())
        startActivity(intent)
     //  this.dismiss()
    }

    private fun getText():String{
        return mBinding?.translatedId?.translatedtext?.text?.toString() ?: "" }


  private fun  toast(value: String){
       Toast.makeText(requireContext(), value, Toast.LENGTH_LONG).show() }

    override fun share() {
        toast("Sharing")
        super.shareData(getText())
    }

        override fun pdf() {
            val activity = mainActivity
            if (activity != null) {
                if (CameraPermissions.isGalleryPermissionGranted(activity)) {
                   // dismiss()
                    activity.showPdf(getText())
                } else {
                    requestGalleryPermission()
                }

            }
        }





    override fun translate() {
        val activity =mainActivity
        if(activity!=null) {
            if (!InternetConnection.isInternetAvailable(activity)) {

                 val action =InternetConnectionDialogDirections.actionGlobalInternetConnectionDialog()
                this.findNavController().navigate(action )

                return } }

        val action= ScanDialogFragmentDirections.actionScanDialogFragmentToLanguageListDialogFragment2(getText())
        this.findNavController().navigate(action )

    }

    override fun startProgress(){}

    override fun stopProgress(){}

    override fun exit() {
         // saveToDataBase()
       // this.dismiss()
    }




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

//            val isShowing =dialog?.isShowing
//            if(isShowing!=null && isShowing ) {
//             toast("Showing ")
//                this.dismiss()
//            }else{
//                toast("Not Showing")
//            }

        }
    }


    override fun onDestroyView() {

        mBinding?.adviewId?.bannerId?.apply {
            this.destroy()
            println("onDestroy"+this)
        }
        mBinding =null
        super.onDestroyView()


    }

     override fun onPause() {
         mBinding?.adviewId?.bannerId?.apply {
            this.pause()

        }
        super.onPause()
    }

     override fun onResume() {
        super.onResume()
         mBinding?.adviewId?.bannerId?.apply {
            this.resume()
        }

     }








}