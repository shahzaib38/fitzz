package imagetrack.app.trackobject.viewmodel

import imagetrack.app.trackobject.navigator.ProgressNavigator
import imagetrack.app.trackobject.repo.MainRepository

class ProgressViewModel @androidx.hilt.lifecycle.ViewModelInject constructor(private val mainRepository : MainRepository) : BaseViewModel<ProgressNavigator>(mainRepository)  {







}