package imagetrack.app.trackobject.ui.fragment


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.core.TorchState
import androidx.camera.core.ZoomState
//import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ext.requestCameraPermission
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.animations.animate
import imagetrack.app.trackobject.camera_features.ICamera
import imagetrack.app.trackobject.camera_features.ScanningCamera
import imagetrack.app.trackobject.databinding.FreeScanFragmentDataBinding
import imagetrack.app.trackobject.databinding.ScanFragmentDataBinding
import imagetrack.app.trackobject.ext.animateFocusRing
import imagetrack.app.trackobject.navigator.ScanNavigator
import imagetrack.app.trackobject.ui.activities.HistoryActivity
import imagetrack.app.trackobject.ui.dialogs.InternetConnectionDialog
import imagetrack.app.trackobject.viewmodel.ScanViewModel
import imagetrack.app.utils.BitmapUtils
import imagetrack.app.utils.CameraPermissions
import imagetrack.app.utils.InternetConnection
import java.io.FileNotFoundException


@ExperimentalGetImage
@ExperimentalUseCaseGroup
//@ExperimentalUseCaseGroupLifecycle
@AndroidEntryPoint
class FreeScanFragment :  BaseFragment<ScanViewModel, FreeScanFragmentDataBinding>() , ScanNavigator {

    private val mViewModel by viewModels<ScanViewModel>()
    private var mScanFragmentDataBinding : FreeScanFragmentDataBinding? = null
    private var iCamera: ICamera?=null


    companion object{
        const val RESULT_LOADING =11122

    }


    override fun getBindingVariable(): Int = imagetrack.app.trackobject.BR.viewModel
    override fun getLayoutId(): Int = R.layout.free_scan_fragment
    override fun getViewModel(): ScanViewModel = mViewModel




    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mScanFragmentDataBinding =getViewDataBinding()






        if (CameraPermissions.isCameraPermissionGranted(requireContext())) startCamera() else requestCameraPermission()
        mViewModel.setNavigator(this)
    }

    private  fun  toast(value: String){
        Toast.makeText(requireContext(), value, Toast.LENGTH_LONG).show()
    }






    private  val torchState =  Observer<Int> {torchState ->
        when(torchState){
            TorchState.OFF -> { changeTorchState(R.drawable.ic_flash_off) }
            TorchState.ON -> { changeTorchState(R.drawable.flashon) }
            else ->{ changeTorchState(R.drawable.ic_flash_off) } } }

    private   fun  changeTorchState(state :Int = R.drawable.ic_flash_off){
        mScanFragmentDataBinding?.include2?.torch?.setImageResource(state) }

    private fun openIntent(){
        toast("Opening Gallery")
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOADING)


    }

    override fun enableTorch() {

    }


    override  fun openGallery(){
        if(InternetConnection.isInternetAvailable(requireActivity())) {
            if (CameraPermissions.isGalleryPermissionGranted(requireContext()))
            { openIntent()
            } else { requestGalleryPermission() }
        }else{
            showInternetConnectionDialog()

        }
    }


    private fun showInternetConnectionDialog(){
        InternetConnectionDialog.getInstance().showDialog(childFragmentManager) }

    override fun capture() {
        iCamera.let {
            if(it is ScanningCamera){
                if(InternetConnection.isInternetAvailable(requireActivity())) {
                    it.captureImage()
                }else{
                    showInternetConnectionDialog()
                }

            } }


    }

    override fun showHistory() {
        val myIntent = Intent(requireContext(), HistoryActivity::class.java)
        this.startActivity(myIntent)    }

    private fun requestGalleryPermission(){
        requestPermissions(
            CameraPermissions.GALLERY_ARRAY,
            CameraPermissions.CAMERA_GALLERY_PERMISSION
        )
    }







    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            try {
                val dataImage = data?.data
                if(dataImage!=null) {
                    val bitmap = BitmapUtils.getBitmap(requireContext() ,dataImage)
                    iCamera?.scanDocument(bitmap)
                } else{
                    toast("Image not selected try again") }

            } catch (e: FileNotFoundException) {
                e.printStackTrace() }
        } else {
            toast("You haven't picked Image") }
    }


    val zoomState =Observer<ZoomState>{ zoomState->


        val zoom =  zoomState.zoomRatio

        val zoomString= String.format("%.1f", zoom)
        val createString = "$zoomString X"

        mScanFragmentDataBinding?.include2?.zoomstate?.text = createString
        mScanFragmentDataBinding?.include2?.zoomstate?.animate(R.anim.fade_in)

    }




    @ExperimentalGetImage
    private fun startCamera() {
//
//        mScanFragmentDataBinding?.include2?.let {previewBinding->
//            iCamera =  mViewModel.provideScanCamera(requireContext(), this, previewBinding.previewFinder,previewBinding. cameraProgress)
//            iCamera?.getZoomState()?.observe(viewLifecycleOwner ,zoomState)
//
//            if(iCamera==null){toast("Restart App "); return}
//            val scaleGestureDetector = ScaleGestureDetector(context, iCamera)
//
//            previewBinding.previewFinder.apply {
//                setOnTouchListener { _, event ->
//                    scaleGestureDetector.onTouchEvent(event)
//
//                    when (event.action) {
//                        MotionEvent.ACTION_DOWN -> return@setOnTouchListener true
//                        MotionEvent.ACTION_UP -> {
//                            val factory = this.meteringPointFactory
//                            val point = factory.createPoint(event.x, event.y)
//                            iCamera?.startFocusAndMetering(point)
//                            previewBinding.focusRing.animateFocusRing(event.x ,event.y)
//
//                            return@setOnTouchListener true }
//                        else -> return@setOnTouchListener false
//                    }
//
//                }
//
//            }
//
//            torchStates()
//            iCamera?.setFragmentManagerr(childFragmentManager)
//        }?:throw NullPointerException("Fragment DataBinding must not be null ")


    }
//
//
//    private  fun torchStates(){
//        iCamera?.getTorchState()?.observe(viewLifecycleOwner, torchState) }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CameraPermissions.CAMERA_PERMISSION) {
            val cameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (cameraPermission) startCamera() else requireActivity().finish()
        }else if(requestCode ==  CameraPermissions.CAMERA_GALLERY_PERMISSION){
            openIntent() }
    }
//
//    override fun enableTorch() {
//        iCamera?.enableTorch() }


}