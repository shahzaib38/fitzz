package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import imagetrack.app.trackobject.navigator.ExitNavigator
import imagetrack.app.trackobject.repo.ExitRepository

class ExitViewModel @ViewModelInject constructor(private val mainRepository: ExitRepository)  : BaseViewModel<ExitNavigator>(mainRepository) {



    fun exit(){

        getNavigator().exit()

    }


    fun close(){
     getNavigator().close()

    }

}