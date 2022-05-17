package imagetrack.app.trackobject.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import imagetrack.app.trackobject.navigator.ScanNavigator
import imagetrack.app.trackobject.repo.ScanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScanViewModel  @ViewModelInject constructor(private val mainRepository : ScanRepository) : BaseViewModel<ScanNavigator>(mainRepository) {


    private var _ScannedText = MutableStateFlow<String>("please wait Scanning Text")
    val scannerText = _ScannedText.asLiveData()


    private var _ProgressMutableLiveData = MutableStateFlow<Boolean>(false)
    val progressLiveData = _ProgressMutableLiveData.asLiveData()

    fun showSettings(){
        getNavigator().showSettings() }

    fun enableTorch(){
        getNavigator().enableTorch() }



    fun openGallery(){
        getNavigator().openGallery() }

    fun capture(){
        getNavigator().capture()

    }


    fun scanText(bitmap : Bitmap){

        viewModelScope.launch(Dispatchers.IO) {

            try {

                progress(true)
                val result = mainRepository.scanText(bitmap )
                updateText(result)



            } catch (exception:Exception){


                val message = exception.message
                if (message != null) {
                    updateText("No Text found try again")
                }


            }finally {

                withContext(Dispatchers.Main) {
                    getNavigator().progressInVisible() }

                progress(false)

            }
        }
    }


    private fun progress(progress:Boolean){
        _ProgressMutableLiveData.update {
            progress }

    }

    fun scanText(imageProxy : ImageProxy) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                   progress(true)
                   val result = mainRepository.scanText(imageProxy)
                   updateText(result)

            } catch (exception:Exception){

                val message = exception.message
                if (message != null) {
                    updateText("No Text found try again")

                }

            }finally {
                 withContext(Dispatchers.Main) {
                     imageProxy.close()
                       getNavigator().progressInVisible()
                   }

                progress(false)

            }
        }
    }



    fun stopImageProcessor() {

        mainRepository.stopImageProcessor()
    }




   private  var mutableLiveData = MutableLiveData<String>()
    val translate : LiveData<String> = mutableLiveData


    fun getUsersFlow(name: MutableMap<String, String> ,
                     startProgress: ()->Unit   ,
    stopProgress :()->Unit ){

        startProgress()
        viewModelScope.launch(Dispatchers.IO) {
            val data =    mainRepository.getUsers(name).getData()
            if(data!=null){
                val translation =   data.getTranslations()

                if(translation!=null){

                    for(translate in translation){

                        if(translate!=null) {
                            val translatedText = translate.getTranslatedText()
                            if(translatedText!=null && translatedText.isNotEmpty()){


                                updateText(translatedText)

                            }

                        }
                    }

                }

            }


            withContext(Dispatchers.Main){
                stopProgress.invoke()

            }


        }


    }

    private fun updateText(text : String) {
        viewModelScope.launch {
            _ScannedText.update{
                text } }
    }


}