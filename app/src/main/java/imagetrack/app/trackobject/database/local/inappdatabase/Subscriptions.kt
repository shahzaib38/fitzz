package imagetrack.app.trackobject.database.local.inappdatabase

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Subscriptions {



    @SerializedName("startTimeMillis")
    @Expose
    var startTimeMillis: String = ""

    @SerializedName("expiryTimeMillis")
    @Expose
    var expiryTimeMillis: String = ""

    @SerializedName("autoRenewing")
    @Expose
    var autoRenewing: Boolean? = null

    @SerializedName("priceCurrencyCode")
    @Expose
    var priceCurrencyCode: String = ""

    @SerializedName("priceAmountMicros")
    @Expose
    var priceAmountMicros: String? = null

    @SerializedName("countryCode")
    @Expose
    var countryCode: String = ""

    @SerializedName("developerPayload")
    @Expose
    var developerPayload: String? = null


    @SerializedName("paymentState")
    @Expose
    var paymentState :Int?=null



    @SerializedName("cancelReason")
    @Expose
    var cancelReason: Int? = null

    @SerializedName("orderId")
    @Expose
    var orderId: String? = null

    @SerializedName("purchaseType")
    @Expose
    var purchaseType: Int? = null

    @SerializedName("acknowledgementState")
    @Expose
    var acknowledgementState: Int? = null

    @SerializedName("kind")
    @Expose
    var kind: String? = null


   fun  activeUntilDate(): Long {
        return this.expiryTimeMillis.toLong() }

    fun isPurchased():Boolean{

        return paymentState ==1
    }

    fun isExpired():Boolean{
        return (System.currentTimeMillis()>=this.expiryTimeMillis.toLong()) }



    fun subscriptionStartedAt():Long{
        return this.startTimeMillis.toLong() }


    fun isFreeTrial():Boolean{
       return  this.paymentState==2 }

    fun isTestMode():Boolean?{

        return (this.purchaseType == 0)
    }

   fun  willRenew(): Boolean? {
        return this.autoRenewing;
   }






}