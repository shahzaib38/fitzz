package imagetrack.app.trackobject.database.local

import imagetrack.app.trackobject.database.local.history.AppDatabase
import imagetrack.app.trackobject.database.network.firebase.AppExecutors
import java.util.concurrent.Executor


class LocalDataSource  constructor(
    private val executor: Executor,
    private val appDatabase: AppDatabase
) {

    /**
     * Get the list of subscriptions from the localDataSource and get notified when the data changes.
     */
    val subscriptions = appDatabase.subscriptionStatusDao().getAll()

    fun updateSubscriptions(subscriptions: List<SubscriptionStatus>) {
        executor.execute({
            appDatabase.runInTransaction {
                // Delete existing subscriptions.
                appDatabase.subscriptionStatusDao().deleteAll()
                // Put new subscriptions data into localDataSource.
                appDatabase.subscriptionStatusDao().insertAll(subscriptions)
            }
        })
    }

    /**
     * Delete local user data when the user signs out.
     */
    fun deleteLocalUserData() = updateSubscriptions(listOf())

    companion object {

        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(executors: AppExecutors, database: AppDatabase): LocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalDataSource(executors.diskIO, database).also { INSTANCE = it }
            }
    }

}