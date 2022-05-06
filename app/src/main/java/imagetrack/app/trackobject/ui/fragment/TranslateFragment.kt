package imagetrack.app.trackobject.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.TranslateDataBinding
import imagetrack.app.trackobject.viewmodel.TranslateViewModel

@AndroidEntryPoint
class TranslateFragment : BaseFragment<TranslateViewModel, TranslateDataBinding>() {

    private val mViewModel by viewModels<TranslateViewModel>()

    override fun getBindingVariable(): Int {
        return BR.viewModel }

    override fun getLayoutId(): Int {
        return R.layout.fragment_translate }

    override fun getViewModel(): TranslateViewModel {
       return mViewModel }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dataBinding =  getViewDataBinding() }



}