package imagetrack.app.trackobject.repo

import imagetrack.app.trackobject.database.local.ILocalDataSource
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.database.local.history.HistoryDatabase
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class HistoryRepository  @Inject constructor(private val historyDatabase: HistoryDatabase ,

                                             private val localDataSource: ILocalDataSource

): BaseRepository() {



    val subscriptionLiveData = localDataSource.getSubscriptionJsonLiveData()






    suspend fun getAll(): List<HistoryBean> =historyDatabase.userDao().getAll()
   suspend fun insertHistory(historyBean : HistoryBean) :Boolean{
       historyDatabase.userDao().insert(historyBean)
       return true}


    suspend fun deleteAll():Boolean{
        historyDatabase.userDao().deleteAll()


        return true
    }


}