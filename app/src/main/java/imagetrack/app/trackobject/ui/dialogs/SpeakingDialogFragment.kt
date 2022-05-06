package imagetrack.app.trackobject.ui.dialogs

import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.SpeakingDataBinding
import imagetrack.app.trackobject.ext.translateText
import imagetrack.app.trackobject.viewmodel.SpeakingViewModel


@AndroidEntryPoint
class SpeakingDialogFragment : BaseDialogFragment<SpeakingViewModel, SpeakingDataBinding>() {

    private var mBinding  : SpeakingDataBinding? =null
    private val mViewModel by viewModels<SpeakingViewModel>()
    override fun getBindingVariable(): Int =BR.viewModel
    override fun getViewModel(): SpeakingViewModel=mViewModel
    override fun getLayoutId(): Int =R.layout.speak_layout

    private  val args: SpeakingDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding =   getViewDataBinding()

        val derivedText =   args.text

        mBinding?.apply {

            this.translatedtext.translateText(derivedText) }





    }



    companion object{
        private const val TAG= "SpeakDialogFFragment"

        fun getInstance(): SpeakingDialogFragment {
            return SpeakingDialogFragment() } }

}