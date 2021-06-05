package imagetrack.app.trackobject.in_app_purchase

import com.android.billingclient.api.Purchase
import imagetrack.app.trackobject.database.local.SubscriptionStatus


fun isPremiumContent(subscription: SubscriptionStatus?) =
    subscription != null &&
            subscription.isEntitlementActive &&
            Constants.PREMIUM_SKU == subscription.sku &&
            !subscription.subAlreadyOwned




fun isBasicContent(subscription: SubscriptionStatus?) =
    subscription != null &&
            subscription.isEntitlementActive &&
            Constants.BASIC_SKU == subscription.sku &&
            !subscription.subAlreadyOwned

fun deviceHasGooglePlaySubscription(purchases: List<Purchase>?, sku: String) =
    purchaseForSku(purchases, sku) != null

fun subscriptionForSku(subscriptions: List<SubscriptionStatus>?, sku: String): SubscriptionStatus? {
    subscriptions?.let {
        for (subscription in it) {
            if (subscription.sku == sku) {
                return subscription
            } else {
                // Do nothing.
            }
        }
    }
    // User does not have the subscription.
    return null
}

fun purchaseForSku(purchases: List<Purchase>?, sku: String): Purchase? {
    purchases?.let {
        for (purchase in it) {
            if (purchase.skus[0] == sku) {
                return purchase
            } else {
                // Do nothing.
            }
        }
    }
    return null
}


fun serverHasSubscription(subscriptions: List<SubscriptionStatus>?, sku: String) =
    subscriptionForSku(subscriptions, sku) != null