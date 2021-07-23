package imagetrack.app.trackobject.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase
import imagetrack.app.trackobject.inapppurchaseUtils.*
import imagetrack.app.trackobject.navigator.SubscriptionStatusNavigator
import imagetrack.app.trackobject.repo.InAppRepository
import imagetrack.app.trackobject.ui.activities.InAppPurchaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InAppViewModel  @ViewModelInject constructor(private val inAppRepository : InAppRepository) : BaseViewModel<SubscriptionStatusNavigator>(inAppRepository) {


    val daoSubscriptions =inAppRepository.purchaseJsonLiveData

    val billingClientLifecycle =inAppRepository.billingClientLifecycle
    val daoSubscriptionJson =inAppRepository.subscriptionLiveData

    val progressLoading =inAppRepository.loading


    val purchases = inAppRepository.purchaseLiveData

    val purchaseUpdate = inAppRepository.purchaseUpdateLiveData
    val skus = inAppRepository.skuDetails



    /**
     * SkuDetails for all known SKUs.
     */
//    private val skusWithSkuDetails =
//        inAppRepository.billingClientLifecycle.skusWithSkuDetails

    /**
     * Subscriptions record according to the server.
     */
     val subscriptions = inAppRepository.subscriptions
    val deniedLiveData =inAppRepository.accessDeniedLiveData

    /**
     * Send an event when the Activity needs to buy something.
     */
    val buyEvent = SingleLiveEvent<BillingFlowParams>()
    val openPlayStoreSubscriptionsEvent = SingleLiveEvent<String>()

    fun openPlayStoreSubscriptions() {
        val hasBasic = deviceHasGooglePlaySubscription(purchases.value, Constants.BASIC_SKU)
        val hasPremium = deviceHasGooglePlaySubscription(purchases.value, Constants.PREMIUM_SKU)
        Log.d("Billing", "hasBasic: $hasBasic, hasPremium: $hasPremium")
        when {
            hasBasic && !hasPremium -> {
                // If we just have a basic subscription, open the basic SKU.
                openPlayStoreSubscriptionsEvent.postValue(Constants.BASIC_SKU)
            }
            !hasBasic && hasPremium -> {
                // If we just have a premium subscription, open the premium SKU.
                openPlayStoreSubscriptionsEvent.postValue(Constants.PREMIUM_SKU)
            }
            else -> {
                // If we do not have an active subscription,
                // or if we have multiple subscriptions, open the default subscription center.
                openPlayStoreSubscriptionsEvent.call()
            }
        }
    }




    fun buyBasic() {
        buy(sku = Constants.BASIC_SKU)
    }


    private fun buy(sku: String =Constants.BASIC_SKU) {
//        // Create the parameters for the purchase.
        val skuDetails = inAppRepository.skuDetails.value?.get(sku) ?: run {
            Log.e("Billing", "Could not find SkuDetails to make purchase.")
            return }
        val billingBuilder = BillingFlowParams.newBuilder().setSkuDetails(skuDetails)

        val billingParams = billingBuilder.build()

        buyEvent.postValue(billingParams)
    }




    // IS Replacable
//    private fun isOldSkuReplaceable(
//        subscriptions: List<SubscriptionStatus>?,
//        purchases: List<Purchase>?,
//        oldSku: String?
//    ): Boolean {
//        if (oldSku == null) return false
//        val isOldSkuOnServer = serverHasSubscription(subscriptions, oldSku)
//        val isOldSkuOnDevice = deviceHasGooglePlaySubscription(purchases, oldSku)
//        return when {
//            !isOldSkuOnDevice -> {
//                Log.e("Billing", "You cannot replace a SKU that is NOT already owned: $oldSku. " +
//                        "This is an error in the application trying to use Google Play Billing.")
//                false
//            }
//            !isOldSkuOnServer -> {
//                Log.i("Billing", "Refusing to replace the old SKU because it is " +
//                        "not registered with the server. Instead just buy the new SKU as an " +
//                        "original purchase. The old SKU might already " +
//                        "be owned by a different app account, and we should not transfer the " +
//                        "subscription without user permission.")
//                false
//            }
//            else -> {
//                val subscription = subscriptionForSku(subscriptions, oldSku) ?: return false
//                if (subscription.subAlreadyOwned) {
//                    Log.i("Billing", "The old subscription is used by a " +
//                            "different app account. However, it was paid for by the same " +
//                            "Google account that is on this device.")
//                    false
//                } else {
//                    true
//                }
//            }
//        }
//    }



    fun insertSubscriptionJson(jsonString :Purchase ){

        viewModelScope.launch(Dispatchers.IO) {
            inAppRepository.insertPurchase(jsonString)
        }
    }


    fun acknowledgeToken(purchaseToken :String){

        inAppRepository.acknowledgeRegisteredPurchaseTokens(purchaseToken)

    }


    fun processInit(purchaseList: List<Purchase>){

        viewModelScope.launch(Dispatchers.IO) {
            inAppRepository.purchaseInit(purchaseList)
        }

        }


   fun  proceed()
   {
       getNavigator().proceed()

   }

    fun launchBillingFlow(inAppPurchaseActivity: InAppPurchaseActivity, billingFlowParams: BillingFlowParams) {


        inAppRepository.launchBillinFlow(inAppPurchaseActivity,billingFlowParams)

    }


    fun processPurchaseList(purchaseList  : List<Purchase> , activity:Activity){

        viewModelScope.launch(Dispatchers.IO) {

            inAppRepository.purchaseInit(purchaseList)

    }}

    fun registerPurchases(purchases: imagetrack.app.trackobject.database.local.inappdatabase.Purchase) {
        viewModelScope.launch(Dispatchers.IO) {

            inAppRepository.registerLocalPurchaseToLocalDataBase(purchases)

        }

    }


}
