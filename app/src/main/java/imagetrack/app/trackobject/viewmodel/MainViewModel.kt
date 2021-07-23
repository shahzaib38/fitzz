package imagetrack.app.trackobject.viewmodel

import android.content.Context
import android.widget.ProgressBar
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.camera.view.PreviewView
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.liveData
import imagetrack.app.trackobject.camera_features.ICamera
import imagetrack.app.trackobject.navigator.MainNavigator
import imagetrack.app.trackobject.repo.MainRepository
import kotlinx.coroutines.Dispatchers

class MainViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : BaseViewModel<MainNavigator>(mainRepository) {



//    val subscriptionDao = mainRepository.daoSubscriptions



   fun  saveNext(){

       getNavigator().exit()

    }

    fun edit(){
        getNavigator().edit()

    }

    fun copy(){
        getNavigator().copy()

    }

    fun share(){
        getNavigator().share()
    }


    fun translate(){
        getNavigator().translate()
    }

    fun pdf(){
    getNavigator().pdf()
}



    fun openGallery(){

        println("GAllery Opened")
    }




    fun getUsers(name: MutableMap<String, String>) = liveData(Dispatchers.IO) {
       // emit(Resource.loading(data = null))


        try {
            mainRepository.getUsers(name).getData()?.getTranslations()?.forEach {


                emit(it?.getTranslatedText())

            }
        } catch (exception: Exception) {

            emit( exception.message ?: "Error Occurred!")
        }


    }



    @ExperimentalUseCaseGroup
    @ExperimentalUseCaseGroupLifecycle
    @ExperimentalGetImage
    fun provideScanCamera(context : Context, lifecycleOwner  : LifecycleOwner, previewView: PreviewView, progress : ProgressBar) :ICamera?{
        return   mainRepository.provideScanCamera(context  ,lifecycleOwner , previewView,progress)

    }

}