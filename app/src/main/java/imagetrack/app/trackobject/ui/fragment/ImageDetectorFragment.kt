package imagetrack.app.trackobject.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.ImageDetectorDataBinding
import imagetrack.app.trackobject.viewmodel.ImageDetectorViewModel

@AndroidEntryPoint
class ImageDetectorFragment : BaseFragment<ImageDetectorViewModel, ImageDetectorDataBinding>() {


    private val mViewModel by viewModels<ImageDetectorViewModel>()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.image_detector
    override fun getViewModel(): ImageDetectorViewModel = mViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewDataBinding()

    }

}