package imagetrack.app.trackobject.database.local.history

interface IDataBaseHelper {

  suspend  fun getHistoryList(): List<HistoryBean>

   suspend fun insert(historyBean: HistoryBean)


   suspend fun delete(historyBean: HistoryBean?)



}