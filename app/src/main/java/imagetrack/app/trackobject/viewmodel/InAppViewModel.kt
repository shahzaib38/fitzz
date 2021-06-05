package imagetrack.app.trackobject.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.in_app_purchase.*
import imagetrack.app.trackobject.navigator.PdfNavigator
import imagetrack.app.trackobject.repo.InAppRepository
import imagetrack.app.trackobject.repo.ScanRepository

class InAppViewModel  @ViewModelInject constructor(private val inAppRepository : InAppRepository) : BaseViewModel<PdfNavigator>(inAppRepository) {


    private val purchases = inAppRepository.billingClientLifecycle.purchases

     val billingLifecycle =inAppRepository.billingClientLifecycle
    /**
     * SkuDetails for all known SKUs.
     */
    private val skusWithSkuDetails =
        inAppRepository.billingClientLifecycle.skusWithSkuDetails

    /**
     * Subscriptions record according to the server.
     */
    private val subscriptions = inAppRepository.subscriptions

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
        println("Basic ")
        val hasBasic = deviceHasGooglePlaySubscription(purchases.value, Constants.BASIC_SKU)
        val hasPremium = deviceHasGooglePlaySubscription(purchases.value, Constants.PREMIUM_SKU)
        Log.d("Billing", "hasBasic: $hasBasic, hasPremium: $hasPremium")
        when {
            hasBasic && hasPremium -> {
                // If the user has both subscriptions, open the basic SKU on Google Play.
                openPlayStoreSubscriptionsEvent.postValue(Constants.BASIC_SKU)
            }
            hasBasic && !hasPremium -> {
                // If the user just has a basic subscription, open the basic SKU on Google Play.
                openPlayStoreSubscriptionsEvent.postValue(Constants.BASIC_SKU)
            }
            !hasBasic && hasPremium -> {
                // If the user just has a premium subscription, downgrade.
                buy(sku = Constants.BASIC_SKU, oldSku = Constants.PREMIUM_SKU)
            }
            else -> {
                // If the user dooes not have a subscription, buy the basic SKU.
                buy(sku = Constants.BASIC_SKU, oldSku = null)

                println("Buying ")
            }
        }
     //   buy(sku = Constants.BASIC_SKU, oldSku = null)

    }

    fun buyPremium() {
        val hasBasic = deviceHasGooglePlaySubscription(purchases.value, Constants.BASIC_SKU)
        val hasPremium = deviceHasGooglePlaySubscription(purchases.value, Constants.PREMIUM_SKU)
        Log.d("Billing", "hasBasic: $hasBasic, hasPremium: $hasPremium")
        when {
            hasBasic && hasPremium -> {
                // If the user has both subscriptions, open the premium SKU on Google Play.
                openPlayStoreSubscriptionsEvent.postValue(Constants.PREMIUM_SKU)
            }
            !hasBasic && hasPremium -> {
                // If the user just has a premium subscription, open the premium SKU on Google Play.
                openPlayStoreSubscriptionsEvent.postValue(Constants.PREMIUM_SKU)
            }
            hasBasic && !hasPremium -> {
                // If the user just has a basic subscription, upgrade.
                buy(sku = Constants.PREMIUM_SKU, oldSku = Constants.BASIC_SKU)
            }
            else -> {
                // If the user does not have a subscription, buy the premium SKU.
                buy(sku = Constants.PREMIUM_SKU, oldSku = null)
            }
        }
    }



    private fun buy(sku: String, oldSku: String?) {
        // First, determine whether the new SKU can be purchased.
        val isSkuOnServer = serverHasSubscription(subscriptions.value, sku)
        val isSkuOnDevice = deviceHasGooglePlaySubscription(purchases.value, sku)
        Log.d("Billing", "$sku - isSkuOnServer: $isSkuOnServer, isSkuOnDevice: $isSkuOnDevice")
        when {
            isSkuOnDevice && isSkuOnServer -> {
                Log.e("Billing", "You cannot buy a SKU that is already owned: $sku. " +
                        "This is an error in the application trying to use Google Play Billing.")
                return
            }
            isSkuOnDevice && !isSkuOnServer -> {
                Log.e("Billing", "The Google Play Billing Library APIs indicate that" +
                        "this SKU is already owned, but the purchase token is not registered " +
                        "with the server. There might be an issue registering the purchase token.")
                return
            }
            !isSkuOnDevice && isSkuOnServer -> {
                Log.w("Billing", "WHOA! The server says that the user already owns " +
                        "this item: $sku. This could be from another Google account. " +
                        "You should warn the user that they are trying to buy something " +
                        "from Google Play that they might already have access to from " +
                        "another purchase, possibly from a different Google account " +
                        "on another device.\n" +
                        "You can choose to block this purchase.\n" +
                        "If you are able to cancel the existing subscription on the server, " +
                        "you should allow the user to subscribe with Google Play, and then " +
                        "cancel the subscription after this new subscription is complete. " +
                        "This will allow the user to seamlessly transition their payment " +
                        "method from an existing payment method to this Google Play account.")
                return
            }
        }

        // Second, determine whether the old SKU can be replaced.
        // If the old SKU cannot be used, set this value to null and ignore it.
        val oldSkuToBeReplaced = if (isOldSkuReplaceable(subscriptions.value, purchases.value, oldSku)) {
            oldSku
        } else {
            null
        }

        // Third, create the billing parameters for the purchase.
        if (sku == oldSkuToBeReplaced) {
            Log.i("Billing", "Re-subscribe.")
        } else if (Constants.PREMIUM_SKU == sku && Constants.BASIC_SKU == oldSkuToBeReplaced) {
            Log.i("Billing", "Upgrade!")
        } else if (Constants.BASIC_SKU == sku && Constants.PREMIUM_SKU == oldSkuToBeReplaced) {
            Log.i("Billing", "Downgrade...")
        } else {
            Log.i("Billing", "Regular purchase.")
        }
        // Create the parameters for the purchase.
        val skuDetails = skusWithSkuDetails.value?.get(sku) ?: run {
            Log.e("Billing", "Could not find SkuDetails to make purchase.")
            return
        }
        val billingBuilder = BillingFlowParams.newBuilder().setSkuDetails(skuDetails)
        // Only set the old SKU parameter if the old SKU is already owned.
        if (oldSkuToBeReplaced != null && oldSkuToBeReplaced != sku) {
            purchaseForSku(purchases.value, oldSkuToBeReplaced)?.apply {
                billingBuilder.setSubscriptionUpdateParams(
                    BillingFlowParams.SubscriptionUpdateParams.newBuilder()
                        .setOldSkuPurchaseToken(purchaseToken)
                        .build())
            }
        }
        val billingParams = billingBuilder.build()

        // Send the parameters to the Activity in order to launch the billing flow.
        buyEvent.postValue(billingParams)
    }




    // IS Replacable
    private fun isOldSkuReplaceable(
        subscriptions: List<SubscriptionStatus>?,
        purchases: List<Purchase>?,
        oldSku: String?
    ): Boolean {
        if (oldSku == null) return false
        val isOldSkuOnServer = serverHasSubscription(subscriptions, oldSku)
        val isOldSkuOnDevice = deviceHasGooglePlaySubscription(purchases, oldSku)
        return when {
            !isOldSkuOnDevice -> {
                Log.e("Billing", "You cannot replace a SKU that is NOT already owned: $oldSku. " +
                        "This is an error in the application trying to use Google Play Billing.")
                false
            }
            !isOldSkuOnServer -> {
                Log.i("Billing", "Refusing to replace the old SKU because it is " +
                        "not registered with the server. Instead just buy the new SKU as an " +
                        "original purchase. The old SKU might already " +
                        "be owned by a different app account, and we should not transfer the " +
                        "subscription without user permission.")
                false
            }
            else -> {
                val subscription = subscriptionForSku(subscriptions, oldSku) ?: return false
                if (subscription.subAlreadyOwned) {
                    Log.i("Billing", "The old subscription is used by a " +
                            "different app account. However, it was paid for by the same " +
                            "Google account that is on this device.")
                    false
                } else {
                    true
                }
            }
        }
    }

}
