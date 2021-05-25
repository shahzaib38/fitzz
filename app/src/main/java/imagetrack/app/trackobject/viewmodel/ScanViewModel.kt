package imagetrack.app.trackobject.viewmodel

import android.content.Context
import android.widget.ProgressBar
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.camera.view.PreviewView
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import imagetrack.app.trackobject.camera_features.ICamera
import imagetrack.app.trackobject.navigator.ScanNavigator
import imagetrack.app.trackobject.repo.ScanRepository

class ScanViewModel  @ViewModelInject constructor(private val mainRepository : ScanRepository) : BaseViewModel<ScanNavigator>(mainRepository) {




    fun enableTorch(){
        getNavigator().enableTorch() }

    fun openGallery(){

        getNavigator().openGallery()
    }

    fun capture(){
        getNavigator().capture()

    }

    @ExperimentalUseCaseGroup
    @ExperimentalUseCaseGroupLifecycle
    @ExperimentalGetImage
    fun provideScanCamera(context : Context, lifecycleOwner  : LifecycleOwner, previewView: PreviewView, progress : ProgressBar) : ICamera?{
        return   mainRepository.provideScanCamera(context  ,lifecycleOwner , previewView,progress)

    }
}