package imagetrack.app.trackobject.camera_features

import android.graphics.Bitmap
import androidx.camera.core.*
//import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import imagetrack.app.CallBack
import imagetrack.app.image_processing.DeviceTextRecognizer
import imagetrack.app.image_processing.VisionImageProcessor
import imagetrack.app.lanuguages.TranslateDetect
import imagetrack.app.trackobject.databinding.LiveFragmentDataBinding
import imagetrack.app.translate.ITranslator
import java.util.concurrent.Executors


@ExperimentalGetImage
@ExperimentalUseCaseGroup
//@ExperimentalUseCaseGroupLifecycle
internal class LiveCameraX  constructor(private val cameraMetaData : CameraMetaData , private val iTranslator : ITranslator?=null ): BaseCameraX(cameraMetaData) ,IImageAnalysisUseCase ,
    CallBack {

    private  var visionImageProcessor= DeviceTextRecognizer(cameraMetaData.getContext() )
    private val translateTextLiveData  =MediatorLiveData<String?>()
    private val progressLiveData  =MutableLiveData<Boolean>()
    private var liveFragmentDataBinding : LiveFragmentDataBinding?=null



    init {

        val viewBindingLocal =    cameraMetaData.getViewDataBinding()

        if(viewBindingLocal is LiveFragmentDataBinding){
            liveFragmentDataBinding =    viewBindingLocal }

//
//        translateTextLiveData.addSource(visionImageProcessor.getTranslateTextLiveData()) {
//            translateTextLiveData.postValue(it) }
//
    }




    override fun stop() {
        visionImageProcessor.run {
            this.stop() } }


    override fun bindAllCameraXUseCases() {
       // super.bindAllCameraXUseCases()
        bindImageAnalysis()
    }

    override fun scanDocument(bitmap: Bitmap) {

    }

    override fun getProgressLiveData(): LiveData<Boolean> = progressLiveData

    override fun getTranslatedLiveData(): LiveData<String?> = translateTextLiveData
    override fun testClick() {


    }

    override fun onDisplayAdded(displayId: Int) {

    }

    override fun onDisplayRemoved(displayId: Int) {
        TODO("Not yet implemented")
    }

    override fun onDisplayChanged(displayId: Int) {
        TODO("Not yet implemented")
    }


    override fun bindImageAnalysis() {
        val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setBackgroundExecutor(Executors.newSingleThreadExecutor())
                .build()
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(),
            { imageProxy ->

                visionImageProcessor.processImageProxy(imageProxy)

            })



    //    super.bindLifecycleView(imageAnalysis)

    }




    override  fun provideSurface(preview : Preview){
        liveFragmentDataBinding?.includePreviewFinder?.previewFinder?.run{
            preview.setSurfaceProvider(this.surfaceProvider) }
    }

    override fun translate(result: String) {
        translateTextLiveData.postValue(result) }

    private val translateObserver = Observer<TranslateDetect>{
     val translation =   it?.getData()?.getTranslations()
     translation?.forEach {

         if(it!=null) {
             translateTextLiveData.postValue(it.getTranslatedText())
         }
     }

    }



}