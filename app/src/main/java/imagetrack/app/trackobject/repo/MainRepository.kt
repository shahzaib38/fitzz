package imagetrack.app.trackobject.repo

import android.content.Context
import android.widget.ProgressBar
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ExperimentalUseCaseGroup
import androidx.camera.lifecycle.ExperimentalUseCaseGroupLifecycle
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import imagetrack.app.trackobject.camera_features.CameraProvider
import imagetrack.app.trackobject.camera_features.ICamera
import imagetrack.app.trackobject.database.local.ILocalDataSource
import imagetrack.app.trackobject.database.local.LocalDataSource
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.database.local.history.HistoryDatabase
import imagetrack.app.translate.TranslateApi
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRepository @Inject constructor(private val translateApi : TranslateApi, private val historyDatabase: HistoryDatabase

                                         , private val localDataSource: ILocalDataSource,
): BaseRepository() {



    val subscriptionLiveData = localDataSource.getSubscriptionJsonLiveData()




    @ExperimentalUseCaseGroup
    @ExperimentalUseCaseGroupLifecycle
    @ExperimentalGetImage
    fun provideScanCamera(context : Context, lifecycleOwner  : LifecycleOwner, previewView: PreviewView,progress : ProgressBar):ICamera?{
        return  CameraProvider.setCamera(context ,  null,lifecycleOwner , previewView,progress) }


    suspend fun getUsers(name: Map<String, String>) = translateApi.getData(name)


    suspend fun insertHistory(historyBean : HistoryBean) :Boolean{
        historyDatabase.userDao().insert(historyBean)
        return true}


//    val daoSubscriptions =localDataSource


}