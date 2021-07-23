package imagetrack.app.trackobject.database.local

import androidx.lifecycle.LiveData
import imagetrack.app.trackobject.database.local.inappdatabase.SubscriptionJson

interface ILocalDataSource {

    fun updatePurchaseSubscriptions(purchaseJson :String)
    fun hasPreviousSubscriptionJson() :Boolean
    fun getSubscriptionJson():SubscriptionStatus
    fun getPurchaseJson(): SubscriptionJson?
    fun hasPreviousPurchaseJson():Boolean
    fun updateSubscriptions(subscriptions: SubscriptionStatus)
    fun getPurchaseJsonLiveData(): LiveData<SubscriptionJson>

    fun getSubscriptionJsonLiveData() : LiveData<SubscriptionStatus>
    fun updatePurchaseSubscription(s: String)
}