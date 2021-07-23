package imagetrack.app.trackobject.inapppurchaseUtils

import com.android.billingclient.api.Purchase
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


//fun isPremiumContent(subscription: SubscriptionStatus?) =
//    subscription != null &&
//            subscription.isEntitlementActive &&
//            Constants.PREMIUM_SKU == subscription.sku &&
//            !subscription.subAlreadyOwned
//
//
//
//
//fun isBasicContent(subscription: SubscriptionStatus?) =
//    subscription != null &&
//            subscription.isEntitlementActive &&
//            Constants.BASIC_SKU == subscription.sku &&
//            !subscription.subAlreadyOwned

 fun processPurchaseList(purchaseList  : List<Purchase> ){

    purchaseList.forEach {purchase ->
        val derivedSkus =  purchase.skus
        derivedSkus.forEach {sku ->

            when(sku){
                Constants.BASIC_SKU ->{
                    // Toast.makeText(this ,"Basic " +sku ,Toast.LENGTH_LONG).show()
                  //  processPurchaseSku(purchase)

                }

                Constants.PREMIUM_SKU ->{
                    //       Toast.makeText(this ,"Premium " +sku ,Toast.LENGTH_LONG).show()
                }
            } } }}




fun deviceHasGooglePlaySubscription(purchases: List<Purchase>?, sku: String) =
    purchaseForSku(purchases, sku) != null

fun purchaseForSku(purchases: List<Purchase>?, sku: String): Purchase? {
    purchases?.let {
        for (purchase in it) {

            println("Purchase ${purchase.originalJson}")

            if (purchase.skus[0] == sku) {
                return purchase
            } else {
                // Do nothing.
            }
        }
    }
    return null
}


fun purchaseForSku(purchaseList : List<Purchase>) :Purchase?{

    purchaseList.let {

        for (purchase in purchaseList) {

            if(purchase.skus[0]==Constants.BASIC_SKU){

                return purchase
            }

            if(purchase.skus[0] ==Constants.PREMIUM_SKU){

                return purchase
            }


        } }
    return null }






fun createTimeNote(subscriptionStatus :SubscriptionStatus):SubscriptionNote {
    val startingTime = getDateTime(subscriptionStatus.startTimeMillis.toLong())
    val expiryTime =getDateTime(subscriptionStatus.expiryTimeMillis.toLong())
    val orderId  = subscriptionStatus.orderId
    return  SubscriptionNote(startingTime,expiryTime ,orderId) }


fun getDateTime(dateInMillis: Long): String {
    val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy, hh:mm a", Locale.getDefault())
    return dateFormat.format(Date(dateInMillis))
}







fun isArrayNullOrEmpty(list : List<Purchase>?):Boolean{

    return list!=null && list.isNotEmpty() }



fun conCatePrice(price :String?,subscriptionPeriod :String?):String{

    return "$price?:$ 4.4 / $subscriptionPeriod?: Monthly Package"
}


//
//fun serverHasSubscription(subscriptions: List<SubscriptionStatus>?, sku: String) =
//    subscriptionForSku(subscriptions, sku) != null