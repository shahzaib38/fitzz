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
import imagetrack.app.trackobject.navigator.LanguageListNavigator
import imagetrack.app.trackobject.repo.MainRepository
import kotlinx.coroutines.Dispatchers

class LanguageListViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : BaseViewModel<LanguageListNavigator>(mainRepository) {



    fun  close(){

        getNavigator().close()

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



}