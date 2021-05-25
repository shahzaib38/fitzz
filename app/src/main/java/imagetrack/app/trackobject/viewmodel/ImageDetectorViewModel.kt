package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import imagetrack.app.trackobject.repo.MainRepository
import imagetrack.app.trackobject.navigator.ImageDetectorNavigator

class ImageDetectorViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) :
    BaseViewModel<ImageDetectorNavigator>(mainRepository) {


}