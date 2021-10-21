package imagetrack.app.trackobject.ui.fragment

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ExperimentalUseCaseGroup
//import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.*
import imagetrack.app.trackobject.camera_features.ICamera
import imagetrack.app.trackobject.camera_features.LangOptions
import imagetrack.app.trackobject.databinding.LiveFragmentDataBinding
import imagetrack.app.trackobject.navigator.LiveNavigator
import imagetrack.app.trackobject.viewmodel.LiveViewModel
import imagetrack.app.utils.CameraPermissions.CAMERA_PERMISSION


@ExperimentalGetImage
@ExperimentalUseCaseGroup
//@ExperimentalUseCaseGroupLifecycle
@AndroidEntryPoint
class LiveFragment : BaseFragment<LiveViewModel, LiveFragmentDataBinding>()  , LiveNavigator{

    private val mViewModel by viewModels<LiveViewModel>()
    private var mLiveFragmentDataBinding: LiveFragmentDataBinding? = null
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.live_fragment
    override fun getViewModel(): LiveViewModel = mViewModel
    private var iCamera: ICamera?=null
    private var langOptions : LangOptions?= LangOptions("English","Urdu")



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("ONCREATED LIVE FRAGMENT ")

        mLiveFragmentDataBinding = getViewDataBinding()
       // val isPermissionGranted =(hasPermissions(requireContext(),CAMERA_PERMISSION_ARRAY))
//        if(isPermissionGranted) startCamera() else requestCameraPermission()
//        mViewModel.setNavigator(this)



    }

//    @ExperimentalGetImage
//    private fun startCamera() {
//
//        val cameraMetaDataBuilder = CameraMetaData.Builder()
//
////        mLiveFragmentDataBinding?.includePreviewFinder
////            ?.previewFinder?.run {
////                cameraMetaDataBuilder
////                .setContext(requireContext())
////                .setLifeCycle(this@LiveFragment)
////                .setViewDataBinding(mLiveFragmentDataBinding!!).build()
////
////
////            iCamera =  mViewModel.provideScanCamera(cameraMetaDataBuilder.build())
////
////            iCamera?.run {
////                iCamera?.startCamera()
////                lifecycle.addObserver(this)
////                this.getTranslatedLiveData().observe(viewLifecycleOwner ,translateTextObserver)
////
////                val scaleGestureDetector = ScaleGestureDetector(context, iCamera)
////
////             mLiveFragmentDataBinding?.includePreviewFinder?.previewFinder?.let {
////
////                 it.setOnTouchListener { _, event ->
////                     scaleGestureDetector.onTouchEvent(event)
////
////                 }}
////            }
////
////
////        }
//
//    }


   private  val translateTextObserver = Observer<String?> { translatedText ->

       mLiveFragmentDataBinding?.includePreviewFinder?.graphics?.let {


           if (translatedText != null && translatedText.isNotEmpty()) {
               it.visibility = View.VISIBLE
               it.text = translatedText
           }
           else
           {
               it.visibility = View.INVISIBLE
           }

           println(translatedText)

       }


    }






    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION) {
            val cameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
        //    if (cameraPermission) startCamera() else requireActivity().finish()



        }
    }

    override fun swap() {
        mLiveFragmentDataBinding?.swapOption()
    }

    override fun translateOption1() {

        iCamera?.testClick()

    }

    override fun translateOption2() {

        langOptions?.first="Urdu"

    }




    fun LiveFragmentDataBinding.swapOption() {
        val optionOneTextView = this.include3.languageOption1
        val optionTwoTextView = this.include3.lanaguageOption2
        val optionOne =  this.include3.languageOption1.text.toString()
        val optionTwo = this.include3.lanaguageOption2.text.toString()
        val swapOption = optionOne
        optionOneTextView.text =optionTwo
        optionTwoTextView.text =swapOption
    }

}

