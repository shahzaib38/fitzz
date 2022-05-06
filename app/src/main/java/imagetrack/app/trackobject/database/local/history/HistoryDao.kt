package imagetrack.app.trackobject.database.local.history

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryBean")
    suspend fun getAll(): List<HistoryBean>

    @Query("SELECT * FROM HistoryBean")
     fun getAllHistoryData(): Flow<List<HistoryBean>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend   fun insert(vararg historyBean: HistoryBean?)

    @Delete
    suspend    fun delete(historyBean: HistoryBean?)

    @Query("DELETE FROM HistoryBean")
    suspend fun deleteAll()


}
