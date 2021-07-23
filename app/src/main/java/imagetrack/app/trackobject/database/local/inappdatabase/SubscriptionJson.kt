package imagetrack.app.trackobject.database.local.inappdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

@Entity
data class SubscriptionJson(
    @PrimaryKey(autoGenerate = false)
    val schoolName :String="",
    val purchaseJson :String=""
    ) {

    fun listFromJsonString(dataString: String): Purchase? {
        val gson = Gson()
        return try {
            gson.fromJson(dataString, Purchase::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    fun checkIsTokenAlreadyAvailable(purchase: com.android.billingclient.api.Purchase):Boolean {
        println("CheckIsTokenAvailable")

      val purchaseJson =  listFromJsonString(purchaseJson)

          return   purchase?.purchaseToken.equals(purchaseJson!!.purchaseToken)





    }

}