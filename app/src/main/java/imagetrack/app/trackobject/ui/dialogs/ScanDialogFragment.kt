package imagetrack.app.trackobject.ui.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ClipBoardManager
import imagetrack.app.lanuguages.TranslateDetect
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.ads.InterstitialAds
import imagetrack.app.trackobject.databinding.ScanDialogDataBinding
import imagetrack.app.trackobject.navigator.MainNavigator
import imagetrack.app.trackobject.viewmodel.MainViewModel
import imagetrack.app.translate.TranslateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.HashMap
import kotlin.coroutines.coroutineContext


@AndroidEntryPoint
 class ScanDialogFragment : BaseDialogFragment<MainViewModel , ScanDialogDataBinding>()  , MainNavigator , DialogInterface.OnDismissListener {


    override fun onDismiss(dialog: DialogInterface) {

    }

    private val mViewModel by viewModels<MainViewModel>()
    private var mBinding  :ScanDialogDataBinding? =null


    override fun getBindingVariable(): Int =BR.viewModel

    override fun getViewModel(): MainViewModel = mViewModel

    override fun getLayoutId(): Int  = R.layout.translated_text

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = getViewDataBinding()

        mViewModel?.setNavigator(this)
        dialog?.setCanceledOnTouchOutside(false);

        val arguments = arguments?.run {
            get(KEY_VALUE) }


        mBinding?.apply {
            translatedtext.setText(arguments as String)
        }?:println("View Data Binding is null")


        InterstitialAds.load(requireContext())
        mBinding?.adView?.run {
            val adRequest = AdRequest.Builder().build()

            this.loadAd(adRequest)
        }?:throw NullPointerException("Banner Ads must not be null ")

    }

        companion object{
        private const val TAG : String="ScanDialogFragment"

            @VisibleForTesting
             const val KEY_VALUE ="textvalue"

        fun getInstance(text : String): ScanDialogFragment {
            val fragmentDialog = ScanDialogFragment()
            val bundle = Bundle()
            bundle.putString(KEY_VALUE, text)

            fragmentDialog.arguments = bundle
            return fragmentDialog
            }
    }


    override fun dismissDialog() {

    }
    override fun copy() {

        ClipBoardManager.clipInstance(requireContext()).copy(mBinding?.translatedtext?.text?.toString()?:"Try again")
   Toast.makeText(requireContext(),"Copied",Toast.LENGTH_LONG).show()
    }

    override fun edit() {

        mBinding?.translatedtext?.run {

            setFocusable(true);
            setEnabled(true);
            setClickable(true);
            setFocusableInTouchMode(true);
        }
        }

   fun  toast(value : String){
       Toast.makeText(requireContext() ,value , Toast.LENGTH_LONG).show()
    }
    override fun share() {

        toast("Sharing")
        super.shareData(mBinding?.translatedtext?.text?.toString()?:"Try again")

    }

    override fun pdf() {
        PdfCreatorDialog.getInstance(mBinding?.translatedtext?.text?.toString()?:"Try again").showDialog(childFragmentManager)
    }





    override fun translate() {
                LanguageListDialogFragment.getInstance(mBinding?.translatedtext?.text?.toString()?:"Try again").showDialog(parentFragmentManager)

        dismiss()

//        this.onDismiss( object  : DialogInterface {
//            override fun cancel() {
//
//            }
//
//            override fun dismiss() {
//                LanguageListDialogFragment.getInstance(mBinding?.translatedtext?.text?.toString()?:"Try again").showDialog(childFragmentManager)
//
//            }
//
//        })
//        startProgress()
//        val postParameters: MutableMap<String, String> = HashMap()
//        postParameters["q"] = mBinding?.translatedtext?.text?.toString()?:"how are you"
//        postParameters["target"] ="de"
//        postParameters["key"] = TranslateUtils.SCANNER_KEY
//        mViewModel.getUsers(postParameters).observe(this, Observer {
//           stopProgress()
//            mBinding?.translatedtext?.also {editText ->
//
//                stopProgress()
//                editText.text.clear()
//                if(it !=null) {
//                    editText.setText(it)
//                    toast("Translated")
//                }
//            }
//
//        })
//
//
    }

    override fun startProgress() {
        mBinding?.progressId?.visibility=View.VISIBLE
    }

    override fun stopProgress() {
        mBinding?.progressId?.visibility=View.GONE
    }

    override fun exit() {
        InterstitialAds.show(requireActivity())
   this.dismiss()
    }

//    override suspend fun sendDataTo(value: String?) {
//
//        withContext(Dispatchers.Main){
//            mBinding?.translatedtext?.also {editText ->
//
//                stopProgress()
//                editText.text.clear()
//                if(value !=null) {
//                    editText.setText(value)
//                    toast("Translated")
//                }
//            }
//
//
//        }
//    }




}