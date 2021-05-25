package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import imagetrack.app.trackobject.repo.LiveRepository
import imagetrack.app.trackobject.navigator.LiveNavigator


class LiveFragmentViewModel  @ViewModelInject constructor (private val liveRepository: LiveRepository) : BaseViewModel<LiveNavigator>(liveRepository) {




}