package imagetrack.app.trackobject.ext

import android.widget.EditText
import android.widget.TextView
import com.android.billingclient.api.SkuDetails
import imagetrack.app.trackobject.inapppurchaseUtils.Constants
import imagetrack.app.trackobject.inapppurchaseUtils.conCatePrice
import imagetrack.app.trackobject.ui.dialogs.ScanDialogFragment


fun TextView.monthlyPackage(map : Map<String,SkuDetails>?) {


    if (map != null) {
        val skuDetails = map[Constants.BASIC_SKU]

        val subscriptionPeriod = skuDetails?.subscriptionPeriod
        val price = skuDetails?.price

        val concatPrice = conCatePrice(price, subscriptionPeriod)
        this.text = concatPrice
    }
}

    fun TextView.translateText(derivedText :String?){
        val isTextNotNullAndEmpty = derivedText !=null && derivedText.isNotEmpty()

        if (isTextNotNullAndEmpty) {
            setText(derivedText)
        } else {
            setText(ScanDialogFragment.NO_TEXT_FOUND)
        }
    }







