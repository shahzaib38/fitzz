package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import imagetrack.app.trackobject.navigator.InstructionNavigator
import imagetrack.app.trackobject.repo.MainRepository

class InstructionViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : BaseViewModel<InstructionNavigator>(mainRepository) {



    fun close(){

        getNavigator().close()



    }



}