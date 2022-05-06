package imagetrack.app.trackobject.viewmodel

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
//import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import imagetrack.app.trackobject.navigator.ScanNavigator
import imagetrack.app.trackobject.repo.ScanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class ScanViewModel  @ViewModelInject constructor(private val mainRepository : ScanRepository) : BaseViewModel<ScanNavigator>(mainRepository) {



//    val translateText  = mainRepository.translateLiveData

    val progressLiveData = mainRepository.progressLiveData


    val mutableTranslatedText =MutableLiveData<String>()

    val translateText =mutableTranslatedText




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

             val derivedFlow = mainRepository.scanText(bitmap)

             derivedFlow.catch {exception ->
                 val message =       exception.message

                 if(message!=null) {

                     mutableTranslatedText.postValue(message)
                 }

                 }.collect {
                 mutableTranslatedText.postValue(it) }

         }
    }

    fun scanText(image: ImageProxy){

        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.scanText(image)
        }



    }

    fun stopImageProcessor() {

        mainRepository.stopImageProcessor()
    }


}