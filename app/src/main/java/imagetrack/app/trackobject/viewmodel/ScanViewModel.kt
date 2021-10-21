package imagetrack.app.trackobject.viewmodel

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
//import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import imagetrack.app.trackobject.navigator.ScanNavigator
import imagetrack.app.trackobject.repo.ScanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScanViewModel  @ViewModelInject constructor(private val mainRepository : ScanRepository) : BaseViewModel<ScanNavigator>(mainRepository) {



    val translateText  = mainRepository.translateLiveData
    val progressLiveData = mainRepository.progressLiveData





    fun showHistory(){
        getNavigator().showHistory() }

    fun enableTorch(){
        getNavigator().enableTorch() }

    fun openGallery(){
        getNavigator().openGallery() }

    fun capture(){
        getNavigator().capture()

    }


    fun scanText(bitmap : Bitmap){

        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.scanText(bitmap)
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