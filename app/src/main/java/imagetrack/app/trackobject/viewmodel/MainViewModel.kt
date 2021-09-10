package imagetrack.app.trackobject.viewmodel


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import imagetrack.app.trackobject.navigator.MainNavigator
import imagetrack.app.trackobject.repo.MainRepository
import kotlinx.coroutines.Dispatchers

class MainViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : BaseViewModel<MainNavigator>(mainRepository) {



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

//        try {
//            mainRepository.getUsers(name).getData()?.getTranslations()?.forEach {
//
//
//                emit(it?.getTranslatedText())
//
//            }
//        } catch (exception: Exception) {
//        emit( exception.message ?: "Error Occurred!")

            emit( "Error Occurred!")
    //    }


    }


}