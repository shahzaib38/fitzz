package imagetrack.app.trackobject.ui.fragment

import android.Manifest
import android.animation.Animator
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.camera_features.ScanningCamera
import imagetrack.app.ext.requestCameraPermission
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.camera_features.ICamera
import imagetrack.app.trackobject.databinding.ScanFragmentDataBinding
import imagetrack.app.trackobject.navigator.ScanNavigator
import imagetrack.app.trackobject.ui.dialogs.InternetConnectionDialog
import imagetrack.app.trackobject.viewmodel.ScanViewModel
import imagetrack.app.utils.CameraPermissions
import imagetrack.app.utils.InternetConnection
import java.io.FileNotFoundException
import java.io.InputStream


@ExperimentalGetImage
@ExperimentalUseCaseGroup
@ExperimentalUseCaseGroupLifecycle
@AndroidEntryPoint
class ScanFragment :  BaseFragment<ScanViewModel, ScanFragmentDataBinding>() , ScanNavigator{

    private val mViewModel by viewModels<ScanViewModel>()
    private var mScanFragmentDataBinding : ScanFragmentDataBinding? = null
    private var iCamera: ICamera?=null


    companion object{
        const val RESULT_LOADING =11122

    }


    override fun getBindingVariable(): Int = imagetrack.app.trackobject.BR.viewModel
    override fun getLayoutId(): Int = R.layout.scan_fragment
    override fun getViewModel(): ScanViewModel = mViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mScanFragmentDataBinding=   getViewDataBinding()
        val isPermissionGranted= (CameraPermissions.hasPermissions(requireContext() , CameraPermissions.CAMERA_PERMISSION_ARRAY))
        if (isPermissionGranted) startCamera() else requestCameraPermission()

        mViewModel.setNavigator(this)

        loadAds()
    }

   private  fun  toast(value : String ){
        Toast.makeText(requireContext() ,value , Toast.LENGTH_LONG).show()
    }

   private  fun loadAds() {
       mScanFragmentDataBinding?.include2?.adView?.run {
           val adRequest = AdRequest.Builder().build()
           this.loadAd(adRequest) } }




   private  val torchState =  Observer<Int> {

        when(it){
            0 ->{

                mScanFragmentDataBinding?.torch?.setImageResource(R.drawable.ic_flash_off)

                println("Flash Off")
            }

            1 ->{
                mScanFragmentDataBinding?.torch?.setImageResource(R.drawable.flashon)
                println("Flash On")

            }

            else ->{
                mScanFragmentDataBinding?.torch?.setImageResource(R.drawable.ic_flash_off)
                println("Default  On")

            }

        }


    }

    private fun openIntent(){
        toast("Opening Gallery")
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOADING)


    }

  override  fun openGallery(){

      if(InternetConnection.isInternetAvailable(requireActivity())) {
          if ((CameraPermissions.hasPermissions(requireContext(), CameraPermissions.GALLERY_ARRAY)))
          { openIntent()
          } else { requestGalleryPermission() }
      }else{

          InternetConnectionDialog.getInstance().showDialog(childFragmentManager)

      }
    }

    override fun capture() {
        iCamera.let {
            if(it is ScanningCamera){

                if(InternetConnection.isInternetAvailable(requireActivity())) {
                    it.captureImage()
                }else{
                    InternetConnectionDialog.getInstance().showDialog(childFragmentManager)

                }

            } }


    }

    private fun requestGalleryPermission(){
        requestPermissions(
            CameraPermissions.GALLERY_ARRAY ?: arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            CameraPermissions.CAMERA_GALLERY_PERMISSION
        )
    }

    val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

            val delta = detector.scaleFactor
            iCamera?.setZoomRatio(delta)

            return true
        }
    }



      private   fun getBitmap(imageUri: Uri?) : Bitmap {

          val imageStream: InputStream? = requireContext().contentResolver.openInputStream(imageUri!!)

          return  BitmapFactory.decodeStream(imageStream)

      }



    private fun processImage(bitmap : Bitmap){
        iCamera?.scanDocument(bitmap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            try {
                   val dataImage = data?.data
                   val bitmap =   getBitmap(dataImage)
                   processImage(bitmap)


            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } else {


            Toast.makeText(requireContext(), "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }

@ExperimentalGetImage
private fun startCamera() {
    val scaleGestureDetector = ScaleGestureDetector(context, listener)


    mScanFragmentDataBinding?.run {
        iCamera =  mViewModel.provideScanCamera(requireContext() , this@ScanFragment,this.include2.previewFinder ,include2.cameraProgress)
        this.include2.previewFinder.apply {
            setOnTouchListener { _, event ->

                scaleGestureDetector.onTouchEvent(event)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> return@setOnTouchListener true
                MotionEvent.ACTION_UP -> {
                    val factory = this.meteringPointFactory

                    val point = factory.createPoint(event.x, event.y)
                    iCamera?.startFocusAndMetering(point)
                    animateFocusRing(event.x , event.y)

                    return@setOnTouchListener true
                }
                else -> return@setOnTouchListener false
            }



            } }

        iCamera?.getCamera()?.cameraInfo?.torchState?.observe( viewLifecycleOwner , torchState)

        iCamera?.setFragmentManagerr(childFragmentManager)






    }

}



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CameraPermissions.CAMERA_PERMISSION) {
            val cameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (cameraPermission) startCamera() else requireActivity().finish()
        }else if(requestCode ==  CameraPermissions.CAMERA_GALLERY_PERMISSION){
            openIntent() }

    }


    private fun animateFocusRing(x1 :Float , y1 : Float){
        mScanFragmentDataBinding?.include2?.focusRing?.apply {
          val width = width
          val height = height
          x = (x1 - width / 2)
          y = (y1 - height / 2)
            println("X   $x")
            println("Y   $y")
          visibility = View.VISIBLE
          alpha = 1F
          animate().setStartDelay(500).setDuration(300).alpha(0F)
              .setListener(object : Animator.AnimatorListener {
                  override fun onAnimationStart(animation: Animator?) {

                  }

                  override fun onAnimationEnd(animation: Animator?) {
                      visibility = View.INVISIBLE
                  }

                  override fun onAnimationCancel(animation: Animator?) {

                  }

                  override fun onAnimationRepeat(animation: Animator?) {

                  }

              })

      }
    }

    override fun enableTorch() {
        iCamera?.enableTorch()

    }


}