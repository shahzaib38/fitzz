package imagetrack.app.trackobject.database.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
data class SubscriptionStatus constructor(
    //Local Field

    @PrimaryKey(autoGenerate =false)
    @Expose(deserialize = false, serialize = false)
    var schoolName: String ="",

    @SerializedName("startTimeMillis")
    @Expose
    var startTimeMillis: String = "",

    @SerializedName("expiryTimeMillis")
@Expose
var expiryTimeMillis: String = "",

@SerializedName("autoRenewing")
@Expose
var autoRenewing: Boolean? = null,

@SerializedName("priceCurrencyCode")
@Expose
var priceCurrencyCode: String = "",

@SerializedName("priceAmountMicros")
@Expose
var priceAmountMicros: String? = null,

@SerializedName("countryCode")
@Expose
var countryCode: String = "",

@SerializedName("developerPayload")
@Expose
var developerPayload: String? = null,


@SerializedName("paymentState")
@Expose
var paymentState :Int?=null,



@SerializedName("cancelReason")
@Expose
var cancelReason: Int? = null,

@SerializedName("orderId")
@Expose
var orderId: String = "",

@SerializedName("purchaseType")
@Expose
var purchaseType: Int? = null,

@SerializedName("acknowledgementState")
@Expose
var acknowledgementState: Int? = null,

@SerializedName("kind")
@Expose
var kind: String? = null,






) {



    fun isAcknowledged():Boolean{
        return acknowledgementState==1 }


    fun isExpired():Boolean{
        return (System.currentTimeMillis()>=this.expiryTimeMillis.toLong()) }






}