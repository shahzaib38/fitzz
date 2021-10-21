package imagetrack.app.trackobject.ui.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.ExitDataBinding
//import imagetrack.app.trackobject.ext.ads
import imagetrack.app.trackobject.navigator.ExitNavigator
import imagetrack.app.trackobject.viewmodel.ExitViewModel


@AndroidEntryPoint
class ExitDialogFragment : BaseDialogFragment<ExitViewModel, ExitDataBinding>()  ,ExitNavigator {


    override fun onDismiss(dialog: DialogInterface) {}

    private val mViewModel by viewModels<ExitViewModel>()
    private var mBinding  : ExitDataBinding? =null



    override fun getBindingVariable(): Int = BR.viewModel
    override fun getViewModel(): ExitViewModel = mViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding =getViewDataBinding()
        mViewModel.setNavigator(this)
        dialog?.setCanceledOnTouchOutside(false)

//              setupAds()
    }


     fun showDialog() {
        super.showDialogs(requireActivity().supportFragmentManager ,TAG)
    }

//    private fun  setupAds(){
//
//        mBinding?.adsInclude?.apply {
//
//            val unitId=    resources.getString(R.string.exit_ads)
//            //adsId.ads(requireContext() ,unitId,advertiseId)
//            lifecycleScope.launch(Dispatchers.IO) {
//                adsId.ads(requireContext(), unitId, advertiseId)
//            }
//        }
//
//    }



    companion object{
        private const val TAG= "ExitDialogFragment"

        fun getInstance(): ExitDialogFragment {
            return ExitDialogFragment() } }

    override fun getLayoutId(): Int  = R.layout.exit_dialog


    override fun exit() {
        requireActivity().finish() }

    override fun close() {
        this.dismiss()
    }

}