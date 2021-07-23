package imagetrack.app.trackobject.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.ConnectionDialogBinding
import imagetrack.app.trackobject.viewmodel.MainViewModel

@AndroidEntryPoint
class InternetConnectionDialog : BaseDialogFragment<MainViewModel, ConnectionDialogBinding>()  {

    private  var bind :ConnectionDialogBinding?=null
    private val mViewModel by viewModels<MainViewModel>()


    companion object{
        private const val TAG :String= "ConnectionFragmentDialog"
        fun getInstance(): InternetConnectionDialog {
            return InternetConnectionDialog() }

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind= getViewDataBinding()
        bind?.ok?.setOnClickListener { dismissDialog() }
    }






    override fun showDialog(fragment: FragmentManager) {
        super.show(fragment ,TAG) }


    override fun getBindingVariable(): Int {
        return  BR.viewModel
    }

    override fun getViewModel(): MainViewModel? =mViewModel

    override fun getLayoutId(): Int {
        return  R.layout.internet_coonection    }

   private   fun dismissDialog() {
        super.dismissDialog(TAG)

    }


}
