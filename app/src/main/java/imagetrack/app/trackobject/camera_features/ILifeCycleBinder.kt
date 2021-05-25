package imagetrack.app.trackobject.camera_features

import androidx.camera.core.UseCase

interface ILifeCycleBinder {

    fun bindLifecycleView( useCase: UseCase)
    fun bindAllCameraXUseCases()

}