package imagetrack.app.trackobject.database.local.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.impl.WorkDatabaseMigrations


@Database(entities = [HistoryBean::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun userDao(): HistoryDao


    companion object {
        private var INSTANCE: HistoryDatabase?=null
        const val HISTORY_DATABASE = "myhistorydatabase"

        fun getInstance(context: Context): HistoryDatabase {
            if (INSTANCE == null) {
                synchronized(HistoryDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            HistoryDatabase::class.java, HISTORY_DATABASE).addMigrations(
                            WorkDatabaseMigrations.MIGRATION_1_2, MIGRATION_2_3).build()
                    }
                }

            }
            return INSTANCE!!

        }

        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Book "
                        + " ADD COLUMN pub_year INTEGER")
            }
        }
    }
}
