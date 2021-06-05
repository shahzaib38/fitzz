package imagetrack.app.trackobject.database.local.history

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.database.local.SubscriptionStatusDao


@Database(entities = [(SubscriptionStatus::class)], version = 4)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subscriptionStatusDao(): SubscriptionStatusDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @VisibleForTesting
        private val DATABASE_NAME = "subscriptions"

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context.applicationContext).also {
                    INSTANCE = it
                }
            }

//        fun getInstance(context: Context): AppDatabase {
//            if (INSTANCE == null) {
//                synchronized(AppDatabase::class.java) {
//                    if (INSTANCE == null) {
//                        INSTANCE = Room.databaseBuilder(context.applicationContext,
//                            AppDatabase::class.java, DATABASE_NAME).addMigrations(
//                            WorkDatabaseMigrations.MIGRATION_1_2, MIGRATION_2_3).build()
//                    }
//                }
//
//            }
//            return INSTANCE!!
//
//        }
//
//        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE Book "
//                        + " ADD COLUMN pub_year INTEGER")
//            }
//        }




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