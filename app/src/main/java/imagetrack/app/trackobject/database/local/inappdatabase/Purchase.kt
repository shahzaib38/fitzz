package imagetrack.app.trackobject.database.local.inappdatabase

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Purchase {
    @SerializedName("orderId")
    @Expose
    var orderId: String = ""

    @SerializedName("packageName")
    @Expose
    var packageName: String = ""

    @SerializedName("productId")
    @Expose
    var productId: String = ""

    @SerializedName("purchaseTime")
    @Expose
    var purchaseTime: Long? = null

    @SerializedName("purchaseState")
    @Expose
    var purchaseState: Int? = null

    @SerializedName("purchaseToken")
    @Expose
    var purchaseToken: String = ""

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null

    @SerializedName("autoRenewing")
    @Expose
    var autoRenewing: Boolean? = null

    @SerializedName("acknowledged")
    @Expose
    var acknowledged: Boolean = false



    fun listFromJsonString(dataString: String): Purchase? {
        val gson = Gson()
        return try {
            gson.fromJson(dataString, Purchase::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }





}