package imagetrack.app.trackobject.repo

import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.database.local.history.HistoryDatabase
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class HistoryRepository  @Inject constructor(private val historyDatabase: HistoryDatabase): BaseRepository() {

    suspend fun getAll(): List<HistoryBean> =historyDatabase.userDao().getAll()
   suspend fun insertHistory(historyBean : HistoryBean) :Boolean{
       historyDatabase.userDao().insert(historyBean)
       return true}



}