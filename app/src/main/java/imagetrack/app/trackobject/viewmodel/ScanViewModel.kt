package imagetrack.app.trackobject.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageProxy
//import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import imagetrack.app.trackobject.navigator.ScanNavigator
import imagetrack.app.trackobject.repo.ScanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class ScanViewModel  @ViewModelInject constructor(private val mainRepository : ScanRepository) : BaseViewModel<ScanNavigator>(mainRepository) {


    private var _ScannedText = MutableLiveData<String>("Scanned Text")
       val scannerText = _ScannedText


       val translateText  =  mainRepository.translateLiveData





    val _ProgressMutableLiveData = MutableSharedFlow<Boolean>()

    val progressLiveData = mainRepository.progressLiveData


    val mutableTranslatedText =MutableLiveData<String>()

 //   val translateText =  mainRepository.translateLiveData




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

                withContext(Dispatchers.Main) {
                    getNavigator().progressVisible()

                }

                mainRepository.scanText(bitmap)

            } catch (exception:Exception){

                val message = exception.message
                if (message != null) {

                }

            }finally {
                withContext(Dispatchers.Main) {
                    getNavigator().progressInVisible()
                }

            }
        }
    }

    fun scanText(image: ImageProxy) {


        viewModelScope.launch(Dispatchers.IO) {

            try {
                mainRepository.clear()
                _ScannedText.postValue("Scanning Text please wait...")

                withContext(Dispatchers.Main) {
                    getNavigator().progressVisible()

                }

                mainRepository.scanText(image)

            } catch (exception:Exception){

                val message = exception.message
                if (message != null) {

                }

            }finally {
                 withContext(Dispatchers.Main) {
                       getNavigator().progressInVisible()
                   }

            }
        }
    }
    fun stopImageProcessor() {

        mainRepository.stopImageProcessor()
    }




    var mutableLiveData = MutableLiveData<String>()
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
                            var translatedText = translate.getTranslatedText()
                            if(translatedText!=null && translatedText.isNotEmpty()){

                                Log.i("test","traslated ${translatedText}")

                                update(translatedText!!)

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

    fun update(it: String) {

        viewModelScope.launch {
            _ScannedText.value= it

        }

    }


}