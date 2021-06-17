//package imagetrack.app.trackobject.ui.activities
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.View
//import android.view.WindowManager
//import androidx.activity.viewModels
//import androidx.lifecycle.Observer
//import com.android.billingclient.api.SkuDetails
//import com.google.firebase.functions.FirebaseFunctions
//import dagger.hilt.android.AndroidEntryPoint
//import imagetrack.app.trackobject.BR
//import imagetrack.app.trackobject.R
//import imagetrack.app.trackobject.databinding.InAppBillingDataBinding
//import java.text.DateFormat
//import java.text.SimpleDateFormat
//import java.util.*
//
//@AndroidEntryPoint
//class InAppPurchaseActivity : BaseActivity<InAppViewModel, InAppBillingDataBinding>() {
//
//    private val mViewModel by viewModels<InAppViewModel>()
//
//    private var mMainDataBinding: InAppBillingDataBinding? = null
//
//    override fun getBindingVariable(): Int = BR.viewModel
//    override fun getLayoutId(): Int = R.layout.in_app_billing_layout
//    override fun getViewModel(): InAppViewModel = mViewModel
//    private val firebaseFunctions = FirebaseFunctions.getInstance()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN)
//
//        mMainDataBinding = getViewDataBinding()
//
//
//        lifecycle.addObserver(mViewModel.billingLifecycle)
//        mViewModel.buyEvent.observe(this, Observer {
//            it?.let {
//                mViewModel.billingLifecycle.launchBillingFlow(this, it)
//            }
//        })
//
//        mViewModel.openPlayStoreSubscriptionsEvent.observe(this, Observer {
//            //  Log.i(TAG, "Viewing subscriptions on the Google Play Store")
//            val sku = it
//            val url = if (sku == null) {
//                // If the SKU is not specified, just open the Google Play subscriptions URL.
//                Constants.PLAY_STORE_SUBSCRIPTION_URL
//            } else {
//                // If the SKU is specified, open the deeplink for this SKU on Google Play.
//                String.format(Constants.PLAY_STORE_SUBSCRIPTION_DEEPLINK_URL, sku, packageName)
//            }
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse(url)
//            startActivity(intent)
//        })
//
//        mViewModel.skus.observe(this, Observer {
//
//         val skuDetails =   it.get(Constants.PREMIUM_SKU)
//            println(skuDetails.toString())
//
//        })
//
//        mViewModel.purchases.observe(this, Observer {
//
//            it.forEach {
//                println("Purchase " +"${it.toString()}")
//            }
//
//          //  println(skuDetails.toString())
//
//        })
//
//     val date =    getDateTime(1623584258138)
//
//        println("Date " + date)
//
//
//        mViewModel.subscriptions.observe(this, Observer {
//
//            it.forEach {
//
//             println("Date "  +getDateTime(it.activeUntilMillisec))
//
//
//            }
//
//
//        })
//
//
//
//    }
//    fun getDateTime(dateInMillis: Long): String? {
//        val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy, hh:mm a", Locale.getDefault())
//        return dateFormat.format(Date(dateInMillis))
//    }
//
//    fun updateSubscriptionUi(){
//
//
//
//        mViewModel.skus.observe(this, Observer {
//
//            getPrice(it, Constants.BASIC_SKU).let { skuDetails ->
//                mMainDataBinding?.weeklyButton?.run {
//
//                    if (skuDetails != null) {
//                        text = skuDetails.price
//
//                    } else {
//                        visibility = View.GONE
//                    }
//
//
//                }
//
//            }
//            getPrice(it, Constants.PREMIUM_SKU).let { skuDetails ->
//                mMainDataBinding?.monthlyButton?.run {
//
//                    if (skuDetails != null) {
//                        text = skuDetails.price
//
//                    } else {
//                        visibility = View.GONE
//                    }
//
//
//                }
//            }
//
//
//        })
//
//
//
//    }
//
//    private fun getPrice(map: Map<String, SkuDetails>, orderId: String):SkuDetails?{
//
//        return map[orderId]
//    }
//
//}