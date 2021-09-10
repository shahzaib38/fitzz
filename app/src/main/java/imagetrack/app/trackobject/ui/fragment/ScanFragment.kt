package imagetrack.app.trackobject.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.window.WindowManager
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ext.requestCameraPermission
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.animations.animate
import imagetrack.app.trackobject.databinding.ScanFragmentDataBinding
import imagetrack.app.trackobject.ext.internetConnectionDialog
import imagetrack.app.trackobject.ext.scaleGestureDetector
import imagetrack.app.trackobject.ext.toast
import imagetrack.app.trackobject.navigator.ScanNavigator
import imagetrack.app.trackobject.ui.activities.MainActivity
import imagetrack.app.trackobject.ui.activities.SettingsActivity
import imagetrack.app.trackobject.ui.dialogs.ScanDialogFragment
import imagetrack.app.trackobject.viewmodel.ScanViewModel
import imagetrack.app.utils.BitmapUtils
import imagetrack.app.utils.CameraPermissions
import imagetrack.app.utils.CameraPermissions.isCameraPermissionGranted
import imagetrack.app.utils.CameraPermissions.isGalleryPermissionGranted
import imagetrack.app.utils.InternetConnection.isInternetAvailable
import java.io.FileNotFoundException
import java.lang.NullPointerException
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


@ExperimentalGetImage
@ExperimentalUseCaseGroup
@AndroidEntryPoint
class ScanFragment :  BaseFragment<ScanViewModel ,
        ScanFragmentDataBinding>() ,
    ScanNavigator {

    private val mViewModel by viewModels<ScanViewModel>()
    private var mScanFragmentDataBinding : ScanFragmentDataBinding? = null

    private var mMainActivity :MainActivity? =null

    private var displayId: Int = -1
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var windowManager: WindowManager

    private val displayManager by lazy {
        requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }


    companion object{


        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0

        const val RESULT_LOADING =11122 }

    override fun getBindingVariable(): Int = imagetrack.app.trackobject.BR.viewModel
    override fun getLayoutId(): Int = R.layout.scan_fragment
    override fun getViewModel(): ScanViewModel = mViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mScanFragmentDataBinding = getViewDataBinding()

        val baseActivitty  = getBaseActivity()
        if(baseActivitty is MainActivity){
            mMainActivity =baseActivitty }

        windowManager = WindowManager(view.context)


        if (isCameraPermissionGranted(requireContext())) startCamera() else requestCameraPermission()
        mViewModel.setNavigator(this)
    }


    private fun displayListener(imageCapture: ImageCapture){

        displayManager.registerDisplayListener( object : DisplayManager.DisplayListener {
            override fun onDisplayAdded(displayId: Int) = Unit
            override fun onDisplayRemoved(displayId: Int) = Unit
            override fun onDisplayChanged(displayId: Int) = view?.let { view ->
                if (displayId == this@ScanFragment.displayId) {
                   // Log.d(TAG, "Rotation changed: ${view.display.rotation}")
                    println("Rotation changed: ${view.display.rotation}")
                    imageCapture.targetRotation = view.display.rotation } } ?: Unit
        }, null)
    }




   private   fun  changeTorchState(state :Int =R.drawable.ic_flash_off){
       mScanFragmentDataBinding?.include2?.torch?.setImageResource(state)
   }

    private fun openIntent(){
        mMainActivity?.toast("Opening Gallery")
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOADING) }


  override  fun openGallery(){
      if(isInternetAvailable(requireActivity())) {
          if (isGalleryPermissionGranted(requireContext()))
          { openIntent()
          } else { requestGalleryPermission() }

      }else{
          mMainActivity?.internetConnectionDialog()

      }
    }



    override fun capture() {

    }


    private fun captureImage(imageCapture: ImageCapture){

        mScanFragmentDataBinding?.actionButtonId?.capture?.setOnClickListener {

            if(isInternetAvailable(requireActivity())) {
                imageCapture.takePicture(Executors.newSingleThreadExecutor(),
                    object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {

                            mViewModel.scanText(image)

                        }

                        override fun onError(exception: ImageCaptureException) {

                            val message =exception.message
                            if(message!=null){

                            mScanFragmentDataBinding?.include2?.cameraProgress?.visibility =View.GONE


                            }
                        }
                    })
            }else{
                mMainActivity?.internetConnectionDialog() }
        } }



    override fun showHistory() {
        val myIntent = Intent(mMainActivity, SettingsActivity::class.java)
        this.startActivity(myIntent)    }

    private fun requestGalleryPermission(){
        requestPermissions(CameraPermissions.GALLERY_ARRAY, CameraPermissions.CAMERA_GALLERY_PERMISSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            try {
                val dataImage = data?.data
                if(dataImage!=null) {
                    val bitmap = BitmapUtils.getBitmap(requireContext() ,dataImage)
                   mViewModel.scanText(bitmap)

                } else{
                    mMainActivity?.toast("Image is not selected try again") }

            } catch (e: FileNotFoundException) {
                e.printStackTrace() }
        } else {
            mMainActivity?.toast("You haven't picked Image") }
    }




@ExperimentalGetImage
private fun startCamera() {

    mScanFragmentDataBinding?.include2?.previewFinder?.post{


      val mdisplay =    mScanFragmentDataBinding?.include2?.previewFinder?.display

        if(mdisplay!=null) {
            displayId =mdisplay.displayId
        }

        setUpCamera()
    }



}

    private fun cameraXLiveData(camera :Camera){
        val cameraController = camera.cameraInfo
        mViewModel.progressLiveData.observe(viewLifecycleOwner, progressStatusObserver)
        cameraController.zoomState.observe(viewLifecycleOwner, zoomState)
        cameraController.torchState.observe(viewLifecycleOwner, torchState)
        mViewModel.translateText.observe(viewLifecycleOwner, translatedObserver)
    }



    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CameraPermissions.CAMERA_PERMISSION) {

            val cameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (cameraPermission) startCamera() else requireActivity().finish()
        }else if(requestCode ==  CameraPermissions.CAMERA_GALLERY_PERMISSION){
            openIntent()}
    }

    override fun enableTorch() {

    }


    override fun onDestroyView() {
        mScanFragmentDataBinding = null
        super.onDestroyView()

    }



    //CameraSetup
    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({

            println("Thread 2"+Thread.currentThread())

            cameraProvider = cameraProviderFuture.get()

            lensFacing = when {
                hasBackCamera() -> CameraSelector.LENS_FACING_BACK
                hasFrontCamera() -> CameraSelector.LENS_FACING_FRONT
                else -> throw IllegalStateException("Back and front camera are unavailable")
            }

            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun hasBackCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false
    }

    private fun hasFrontCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ?: false
    }
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        bindCameraUseCases()
    }

    //CameraUseCases
    private fun bindCameraUseCases() {

      val windowManager =  WindowManager(requireContext()).getCurrentWindowMetrics()
//         Get screen metrics used to setup camera for full screen resolution
        val metrics = windowManager.bounds

        val screenAspectRatio = aspectRatio(metrics.width(), metrics.height())
    //     Log.d(TAG, "Preview aspect ratio: $screenAspectRatio")

        val rotation = mScanFragmentDataBinding?.include2?.previewFinder?.display?.rotation

        // CameraProvider
        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        // CameraSelector
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        // Preview
    val preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation?:android.view.Surface.ROTATION_90)
            .build()

        // ImageCapture
  val   imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation?:android.view.Surface.ROTATION_90)
            .build()

        cameraProvider.unbindAll()

        try {
          val camera =  cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

            cameraXLiveData(camera)
            captureImage(imageCapture)
            torch(camera)
            displayListener(imageCapture)


            mScanFragmentDataBinding?.scaleGestureDetector( requireContext(),camera)

            // Attach the viewfinder's surface provider to preview use case
            preview.setSurfaceProvider(mScanFragmentDataBinding?.include2?.previewFinder?.surfaceProvider)

        } catch (exc: Exception) {
         //   Log.e(CameraFragment.TAG, "Use case binding failed", exc)


        }
    }

    private fun torch(camera : Camera){
      val cameraController =  camera.cameraControl
        val cameraInfo =camera.cameraInfo

        mScanFragmentDataBinding?.include2?.torch?.setOnClickListener {
            when(cameraInfo.torchState.value) {
                0->{
                    cameraController.enableTorch(true) }

                1-> {
                    cameraController.enableTorch(false) }
                else-> {
                    cameraController.enableTorch(false) } }
        } }



    /****
     * Observers
     */

    //ZoomLiveData
    private  val zoomState =Observer<ZoomState>{zoomState->
        if(zoomState!=null) {
            val zoom = zoomState.zoomRatio
            val zoomString = String.format("%.1f", zoom)
            val createString = "$zoomString X"
            mScanFragmentDataBinding?.include2?.zoomstate?.text = createString
            mScanFragmentDataBinding?.include2?.zoomstate?.animate(R.anim.fade_in)
        }
    }




    //TranslateLiveData
    private val translatedObserver = Observer<String?>{
        if(it!=null){
            ScanDialogFragment
                .getInstance(it)
                .showDialog(requireActivity().supportFragmentManager) } }





    //ProgressLiveData
    private val progressStatusObserver =Observer<Boolean>{progressStatus ->
        if(progressStatus!=null) {
            when (progressStatus) {
                true -> {
                    mScanFragmentDataBinding?.include2?.cameraProgress?.visibility =View.VISIBLE }
                false -> {
                    mScanFragmentDataBinding?.include2?.cameraProgress?.visibility =View.GONE }
            }}
    }


    //TorchLiveData
    private  val torchState =  Observer<Int> {torchState ->
        if(torchState!=null) {
            when (torchState) {
                TorchState.OFF -> {
                    changeTorchState(R.drawable.ic_flash_off)
                }
                TorchState.ON -> {
                    changeTorchState(R.drawable.flashon)
                }
                else -> {
                    changeTorchState(R.drawable.ic_flash_off)
                }
            }

        }

    }





}