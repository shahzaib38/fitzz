package imagetrack.app.trackobject.repo

//import imagetrack.app.trackobject.camera_features.CameraProvider
import imagetrack.app.trackobject.database.local.ILocalDataSource
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



    suspend fun getUsers(name: Map<String, String>) = translateApi.getData(name)


    suspend fun insertHistory(historyBean : HistoryBean) :Boolean{
        historyDatabase.userDao().insert(historyBean)
        return true}




}