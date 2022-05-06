package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import imagetrack.app.trackobject.navigator.ScanDialogNavigator
import imagetrack.app.trackobject.repo.MainRepository

class SpeakingViewModel  @ViewModelInject constructor(private val mainRepository: MainRepository)  : BaseViewModel<ScanDialogNavigator>(mainRepository) {

}