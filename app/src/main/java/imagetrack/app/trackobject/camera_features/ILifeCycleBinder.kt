package imagetrack.app.trackobject.camera_features

import androidx.camera.core.UseCase

interface ILifeCycleBinder {

    fun bindLifecycleView( useCase: UseCase,useCase2: UseCase)
    fun bindAllCameraXUseCases()

}