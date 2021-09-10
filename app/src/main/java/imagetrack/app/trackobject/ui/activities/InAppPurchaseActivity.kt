package imagetrack.app.trackobject.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.database.local.inappdatabase.SubscriptionJson
import imagetrack.app.trackobject.databinding.InAppBillingDataBinding
//import imagetrack.app.trackobject.ext.ads
import imagetrack.app.trackobject.ext.internetConnectionDialog
import imagetrack.app.trackobject.inapppurchaseUtils.Constants
import imagetrack.app.trackobject.inapppurchaseUtils.createTimeNote
import imagetrack.app.trackobject.navigator.SubscriptionStatusNavigator
import imagetrack.app.trackobject.ui.dialogs.SubscriptionStatusDialog
import imagetrack.app.trackobject.viewmodel.InAppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InAppPurchaseActivity : BaseActivity<InAppViewModel, InAppBillingDataBinding>() ,SubscriptionStatusNavigator {

    private val mViewModel by viewModels<InAppViewModel>()

    private var mMainDataBinding: InAppBillingDataBinding? = null

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.in_app_billing_layout
    override fun getViewModel(): InAppViewModel = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            mMainDataBinding = getViewDataBinding()
            lifecycle.addObserver(mViewModel.billingClientLifecycle)
            mViewModel.setNavigator(this)
            mViewModel.buyEvent.observe(this, billingFlowObserver)
            mViewModel.progressLoading.observe(this ,progressObserver)
            mViewModel.daoSubscriptionJson.observe(this, daoSubscriptionObserver)
            mViewModel.purchases.observe(this, intialPurchaseObserver)
            mViewModel.purchaseUpdate.observe(this, purchaseUpdateObserver)
            mViewModel.daoSubscriptions.observe(this, jsonObserver)
            mViewModel.deniedLiveData.observe(this, deniedFlowObserver)
             mViewModel.skus.observe(this ,skuObserver)


//        setupAds()
    }

    private val skuObserver = Observer<Map<String,SkuDetails>> { sku ->

        if(sku!=null){
            val skuDetails = sku[Constants.BASIC_SKU]
            if(skuDetails!=null) {
                mMainDataBinding?.include4?.textView18?.buyConcat(skuDetails)


            } } }

  private   fun TextView.buyConcat(skuDetails: SkuDetails){
      val price = skuDetails.price
      val  concatString = price + "/month"
      this.text = concatString }
//
//    private fun  setupAds(){
//
//        mMainDataBinding?.adsInclude?.apply {
//
//            val unitId=    resources.getString(R.string.subscription_native)
//            lifecycleScope.launch(Dispatchers.IO) {
//                adsId.ads(this@InAppPurchaseActivity, unitId, advertiseId)
//            }
//        }
//
//    }



    private fun startProgress(){
        mMainDataBinding?.progressId?.visibility = View.VISIBLE }

    private fun stopProgress(){
        mMainDataBinding?.progressId?.visibility = View.INVISIBLE }


    private val progressObserver= Observer<Boolean>{progressStatus ->

        if(progressStatus!=null ){
            Log.i("InAppPurchase" , "Progress is not null")

            if(progressStatus) {

                Log.i("InAppPurchase" , "true")
                startProgress()
            }else{
                Log.i("InAppPurchase" , "true")

                stopProgress()
            }


        }
    }

    private fun showInternetConnectionDialog(){
        this.internetConnectionDialog()
//        InternetConnectionDialog.getInstance().showDialog(supportFragmentManager)


    }

    private val daoSubscriptionObserver = Observer<SubscriptionStatus> { subscriptionStatus ->
        Log.i("InAppPurchaseActivity" , "SubscriptionStatus")


        if(subscriptionStatus!=null) {
           val subscriptionNote = createTimeNote(subscriptionStatus)
            if (!subscriptionStatus.isExpired()) {
                SubscriptionStatusDialog.getInstance(subscriptionNote).showDialog(supportFragmentManager)
            }

        }

    }
    private fun openIntent(){
        finish()
        val intent = Intent(this ,MainActivity::class.java)
        startActivity(intent)


    }


    private val intialPurchaseObserver = Observer<List<Purchase>>{ purchaseList->
        Log.i("InAppPurchaseActivity" , "intialPurchaseObserver")

        if(purchaseList!=null && purchaseList.isNotEmpty()) {
            Log.i("InAppPurchaseActivity" , "intialPurchaseObserver Second")

            mViewModel.processInit(purchaseList)

        }

    }

    private val deniedFlowObserver =Observer<String>{


    }


    private val billingFlowObserver =Observer<BillingFlowParams> {
        Log.i("InAppPurchaseActivity" , "BillingFlowObserver")
        it?.let {
            mViewModel.launchBillingFlow(this,it) } }

    private val purchaseUpdateObserver =Observer<List<Purchase>> {purchaseList->
        Log.i("InAppPurchaseActivity" , "Purchase Update Observer")
        purchaseList?.let {
            mViewModel.processPurchaseList(purchaseList ,this ) } }


    private val jsonObserver =Observer<SubscriptionJson>{subscriptionJson->
        Log.i("InAppPurchaseActivity" , "JsonObserver")
        if(subscriptionJson!=null){
                val purchases =subscriptionJson.listFromJsonString(subscriptionJson.purchaseJson)
                if(purchases!=null){
                    if(!purchases.acknowledged) {
                        mViewModel.acknowledgeToken(purchases.purchaseToken)
                    }
                    mViewModel.registerPurchases(purchases )
                }


            } }


  private   fun toast(name :String ){
        Toast.makeText(this ,name,Toast.LENGTH_LONG).show() }

    override fun proceed() {

    }

}