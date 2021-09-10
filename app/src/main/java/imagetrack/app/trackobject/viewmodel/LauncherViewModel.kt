package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import imagetrack.app.trackobject.navigator.LiveNavigator
import imagetrack.app.trackobject.repo.LauncherRepository

class LauncherViewModel   @ViewModelInject constructor (private val launcherRepository: LauncherRepository) : BaseViewModel<LiveNavigator>(launcherRepository) {


}