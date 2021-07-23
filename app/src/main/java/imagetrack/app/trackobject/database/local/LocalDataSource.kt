package imagetrack.app.trackobject.database.local

import androidx.lifecycle.LiveData
import imagetrack.app.trackobject.database.local.inappdatabase.AppDatabase
import imagetrack.app.trackobject.database.local.inappdatabase.SubscriptionJson
import imagetrack.app.trackobject.inapppurchaseUtils.AppExecutors
import java.util.concurrent.Executor


open class LocalDataSource  constructor(
    private val executor: Executor,
    private val appDatabase: AppDatabase
) : ILocalDataSource{

    /**
     * Get the list of subscriptions from the localDataSource and get notified when the data changes.
     */
   private val subscriptions = appDatabase.subscriptionStatusDao().getSubscriptionsStatusData()

   private val purchaseJson =appDatabase.subscriptionStatusDao().getSubscriptionsJson()


  override  fun getPurchaseJsonLiveData(): LiveData<SubscriptionJson>
    {
        return purchaseJson }


   override fun getSubscriptionJsonLiveData() : LiveData<SubscriptionStatus>{

        return subscriptions }

    override fun updatePurchaseSubscription(s: String) {

    }


    override fun updateSubscriptions(subscriptions: SubscriptionStatus) {
        executor.execute({
            appDatabase.runInTransaction {
                // Delete existing subscriptions.
                appDatabase.subscriptionStatusDao().deleteSubscriptionStatusAll()
                // Put new subscriptions data into localDataSource.
                appDatabase.subscriptionStatusDao().insertSubscriptionStatus(subscriptions)
            }
        })
    }

    override   fun hasPreviousPurchaseJson():Boolean =try{
            purchaseJson.value
            true
        }catch (e : NullPointerException) {
            false
        }

    override fun getPurchaseJson():SubscriptionJson?{
        return purchaseJson.value
    }

    override fun getSubscriptionJson():SubscriptionStatus{

        return subscriptions.value!!
    }



    override  fun hasPreviousSubscriptionJson() :Boolean = try{
            subscriptions.value
            true
        }catch (e :NullPointerException){
            false }



    override fun updatePurchaseSubscriptions(purchaseJson :String){
        executor.execute({
            appDatabase.runInTransaction {
                // Delete existing subscriptions.
                appDatabase.subscriptionStatusDao().deleteSubscriptionJsonAll()
                // Put new subscriptions data into localDataSource.
                appDatabase.subscriptionStatusDao().insertSubscriptionJson(SubscriptionJson("purchase",purchaseJson))
            }
        })

    }

    /**
     * Delete local user data when the user signs out.
     */
//    fun deleteLocalUserData() = updateSubscriptions(listOf())

    companion object {

        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(executors: AppExecutors, database: AppDatabase): LocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalDataSource(executors.diskIO, database).also { INSTANCE = it }
            }
    }

}