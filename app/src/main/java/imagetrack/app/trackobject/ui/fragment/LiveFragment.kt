package imagetrack.app.trackobject.ui.fragment

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ext.requestCameraPermission
import imagetrack.app.trackobject.*
import imagetrack.app.trackobject.databinding.LiveFragmentDataBinding
import imagetrack.app.trackobject.viewmodel.MainViewModel
import imagetrack.app.utils.CameraPermissions.CAMERA_PERMISSION
import imagetrack.app.utils.CameraPermissions.CAMERA_PERMISSION_ARRAY
import imagetrack.app.utils.CameraPermissions.hasPermissions


@ExperimentalGetImage
@ExperimentalUseCaseGroup
@ExperimentalUseCaseGroupLifecycle
@AndroidEntryPoint
class LiveFragment : BaseFragment<MainViewModel, LiveFragmentDataBinding>() {

    private val mViewModel by viewModels<MainViewModel>()
    private var mLiveFragmentDataBinding: LiveFragmentDataBinding? = null
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.live_fragment
    override fun getViewModel(): MainViewModel = mViewModel


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLiveFragmentDataBinding = getViewDataBinding()
        val isPermissionGranted =(hasPermissions(requireContext(),CAMERA_PERMISSION_ARRAY))
        if(isPermissionGranted) startCamera() else requestCameraPermission()
    }

    @ExperimentalGetImage
    private fun startCamera() {
//        mLiveFragmentDataBinding?.run {
//       val iCamera=   mViewModel.provideCamera(requireContext() ,this.graphics ,this@LiveFragment,this.previewFinder)
//            this@LiveFragment.lifecycle.addObserver(iCamera!!)
//
//        }

    }






    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION) {
            val cameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (cameraPermission) startCamera() else requireActivity().finish()
        }
    }



}