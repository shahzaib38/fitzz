package imagetrack.app.trackobject.camera_features

import androidx.camera.core.Preview

interface IPreview {

  fun  providePreviewUseCase()
    fun provideSurface(preview : Preview)
}