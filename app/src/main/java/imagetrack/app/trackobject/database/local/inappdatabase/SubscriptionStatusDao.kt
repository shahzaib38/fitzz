package imagetrack.app.trackobject.database.local

import androidx.lifecycle.LiveData
import androidx.room.*
import imagetrack.app.trackobject.database.local.inappdatabase.SubscriptionJson

@Dao
interface SubscriptionStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertSubscriptionJson(subscriptionJson  : SubscriptionJson)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertSubscriptionStatus(subscriptionsStatus: SubscriptionStatus)

    @Transaction
    @Query("SELECT * FROM SubscriptionJson")
     fun getSubscriptionsJson(): LiveData<SubscriptionJson>


    @Transaction
    @Query("SELECT * FROM SubscriptionStatus")
     fun getSubscriptionsStatusData(): LiveData<SubscriptionStatus>




//
//    @Query("SELECT * FROM subscriptions")
//    fun getAll(): LiveData<SubscriptionStatus>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(comments: SubscriptionStatus)
//
    @Query("DELETE FROM SubscriptionJson")
    fun deleteSubscriptionJsonAll()

    @Query("DELETE FROM SubscriptionStatus")
    fun deleteSubscriptionStatusAll()


}