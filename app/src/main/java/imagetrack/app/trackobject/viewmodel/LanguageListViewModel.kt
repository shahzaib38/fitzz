package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import imagetrack.app.trackobject.navigator.LanguageListNavigator
import imagetrack.app.trackobject.repo.MainRepository
import kotlinx.coroutines.Dispatchers

class LanguageListViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : BaseViewModel<LanguageListNavigator>(mainRepository) {



    fun  close(){
        getNavigator().close()

    }



    fun getUsers(name: MutableMap<String, String>) = liveData(Dispatchers.IO) {
        try {
            mainRepository.getUsers(name).getData()?.getTranslations()?.forEach {
             emit(it?.getTranslatedText()) }

        } catch (exception: Exception) {
            emit( exception.message ?: "Error Occurred!")
       // emit( "Error Occurred!")

         }


    }



}