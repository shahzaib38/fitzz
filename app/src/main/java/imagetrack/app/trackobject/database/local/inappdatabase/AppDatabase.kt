package imagetrack.app.trackobject.database.local.inappdatabase

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.database.local.SubscriptionStatusDao


@Database(entities = [SubscriptionStatus::class ,SubscriptionJson::class], version = 4 ,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subscriptionStatusDao(): SubscriptionStatusDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @VisibleForTesting
        private val DATABASE_NAME = "subscriptions-db"

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context.applicationContext).also {
                    INSTANCE = it
                }
            }

        /**
         * Set up the database configuration.
         * The SQLite database is only created when it's accessed for the first time.
         */
        private fun buildDatabase(appContext: Context): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}