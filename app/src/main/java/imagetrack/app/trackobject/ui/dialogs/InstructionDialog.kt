package imagetrack.app.trackobject.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.datastore.IDataStore
import imagetrack.app.datastore.WelcomeNote
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.InstructionDataBinding
import imagetrack.app.trackobject.ext.ads
import imagetrack.app.trackobject.navigator.InstructionNavigator
import imagetrack.app.trackobject.viewmodel.InstructionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InstructionDialog  : BaseDialogFragment<InstructionViewModel, InstructionDataBinding>() , InstructionNavigator{

    private  var mInstructionDataBinding : InstructionDataBinding?=null

    private val mViewModel by viewModels<InstructionViewModel>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mInstructionDataBinding =  getViewDataBinding()

        mViewModel.setNavigator(this)
        setupAds()


    }


    private fun  setupAds(){
        mInstructionDataBinding?.adsInclude?.apply {
            val unitId=    resources.getString(R.string.settings_native)
                adsId.ads( lifecycleScope,requireContext(), unitId, advertiseId) }
    }


    override fun getBindingVariable(): Int = BR.viewModel
    override fun getViewModel(): InstructionViewModel? = mViewModel
    override fun getLayoutId(): Int = R.layout.instruction_layout



    companion object{
        private const val TAG :String= "InstructionFragmentDialog"
        fun getInstance(): InstructionDialog {
            return InstructionDialog() }
    }



    fun showDialog(fragmentManager: FragmentManager) {
        super.showDialogs(fragmentManager ,TAG) }


    private   fun dismissDialog() {
        super.dismissDialog(TAG) }

    override fun close() {
        lifecycleScope.launch(Dispatchers.IO) {
            IDataStore.getInstance(requireContext().applicationContext).save(WelcomeNote(true))
        }
        this.dismiss()
    }

    override fun onDestroyView() {
        mInstructionDataBinding = null
        println("instruction Dialog ")
        super.onDestroyView()
    }



}