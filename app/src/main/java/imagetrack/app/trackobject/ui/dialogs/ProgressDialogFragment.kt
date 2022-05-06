package imagetrack.app.trackobject.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.preferences.AdThreshold
import imagetrack.app.trackobject.databinding.ProgressDataBinding
import imagetrack.app.trackobject.ext.ads
import imagetrack.app.trackobject.ui.activities.MainActivity
import imagetrack.app.trackobject.viewmodel.ProgressViewModel

@AndroidEntryPoint
class ProgressDialogFragment : BaseDialogFragment<ProgressViewModel, ProgressDataBinding>() {

    private val mViewModel by viewModels<ProgressViewModel>()
    private  var mProgressDataBinding : ProgressDataBinding?=null
    private var mainActivity :MainActivity?=null

    override fun getBindingVariable(): Int =BR.viewModel
    override fun getViewModel(): ProgressViewModel? =mViewModel
    override fun getLayoutId(): Int = R.layout.progress_layout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProgressDataBinding =  getViewDataBinding()

     val baseActivity =   getBaseActivity()

        if(baseActivity is MainActivity){
            mainActivity = baseActivity
        }


//        mainActivity?.let {
//            if (!AdThreshold.getInstance(it).isMaxClickedPerformed()) {
//                setupAds()
//            }
//
//        }


    }



//
//    private fun  setupAds(){
//        mProgressDataBinding?.adsInclude?.apply {
//            val unitId=    resources.getString(R.string.pdf_creator)
//                adsId.ads(lifecycleScope ,requireContext(), unitId, advertiseId) }
//    }


    override fun onDestroyView() {
        mProgressDataBinding =null
        println("Progress Dialog ")
        super.onDestroyView()

    }


    fun showDialog(fragmentManager: FragmentManager) {
            super.showDialogs(fragmentManager ,TAG)

    }


    companion object{
        private const val TAG :String= "ProgressDialogFragment"

        fun getInstance(): ProgressDialogFragment {
            return ProgressDialogFragment() }
    }


}