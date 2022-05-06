package imagetrack.app.trackobject.ui.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.database.local.inappdatabase.SubscriptionJson
import imagetrack.app.trackobject.database.preferences.AdThreshold
import imagetrack.app.trackobject.databinding.InAppBillingDataBinding
import imagetrack.app.trackobject.ext.subscriptionStatusDialog
import imagetrack.app.trackobject.inapppurchaseUtils.Constants
import imagetrack.app.trackobject.inapppurchaseUtils.createTimeNote
import imagetrack.app.trackobject.navigator.SubscriptionStatusNavigator
import imagetrack.app.trackobject.viewmodel.InAppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InAppPurchaseActivity : BaseActivity<InAppViewModel, InAppBillingDataBinding>() ,SubscriptionStatusNavigator , FragmentManager.OnBackStackChangedListener {

    private val mViewModel by viewModels<InAppViewModel>()

    private var mInAppBillingDataBinding: InAppBillingDataBinding? = null

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.in_app_billing_layout
    override fun getViewModel(): InAppViewModel = mViewModel


    override fun onBackStackChanged() {

//        val count = this.supportFragmentManager.backStackEntryCount
//        Log.i("count" , "${count} ")
//        for(i :Int in 0 until count step 1){
//
//            val entry =this.supportFragmentManager?.getBackStackEntryAt(i)
//
//            Log.i("count entry" , "${entry.name } ")
//
//        }
//        println("BackStack Changed ")
//
//

    }











    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mInAppBillingDataBinding = getViewDataBinding()
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



        if(!AdThreshold.getInstance(this).isMaxClickedPerformed()) {
            setupAds() }

        this.supportFragmentManager.addOnBackStackChangedListener(this)
    }

    private val skuObserver = Observer<Map<String,SkuDetails>> { sku ->

        if(sku!=null){
            val skuDetails = sku[Constants.BASIC_SKU]
            if(skuDetails!=null) {
                mInAppBillingDataBinding?.include4?.textView18?.buyConcat(skuDetails)


            } } }

  private   fun TextView.buyConcat(skuDetails: SkuDetails){
      val price = skuDetails.price
      val  concatString = price + "/month"
      this.text = concatString }

    private fun  setupAds(){
        mInAppBillingDataBinding?.adsInclude?.apply {
            val adRequest = AdRequest.Builder().build()
            this.loadAd(adRequest)
            this.adListener =object : AdListener(){

                override fun onAdClicked() {
                    super.onAdClicked()
                    lifecycleScope.launch(Dispatchers.IO){
                        AdThreshold.getInstance(this@InAppPurchaseActivity).save(1) }
                }

            }
             }
    }



    private fun startProgress(){
        mInAppBillingDataBinding?.progressId?.visibility = View.VISIBLE }

    private fun stopProgress(){
        mInAppBillingDataBinding?.progressId?.visibility = View.INVISIBLE }


    private val progressObserver= Observer<Boolean>{progressStatus ->

        if(progressStatus!=null ){

            if(progressStatus) {
                startProgress()
            }else{
                stopProgress()
            }


        }
    }



    private val daoSubscriptionObserver = Observer<SubscriptionStatus> { subscriptionStatus ->

        if(subscriptionStatus!=null) {
           val subscriptionNote = createTimeNote(subscriptionStatus)
            if (!subscriptionStatus.isExpired()) {

                this.subscriptionStatusDialog(subscriptionNote) } } }

    private val intialPurchaseObserver = Observer<List<Purchase>>{ purchaseList->

        if(purchaseList!=null && purchaseList.isNotEmpty()) {

            mViewModel.processInit(purchaseList)

        }

    }

    private val deniedFlowObserver =Observer<String>{


    }


    private val billingFlowObserver =Observer<BillingFlowParams> {
        it?.let {
            mViewModel.launchBillingFlow(this,it) } }

    private val purchaseUpdateObserver =Observer<List<Purchase>> {purchaseList->
        purchaseList?.let {
            mViewModel.processPurchaseList(purchaseList ,this ) } }


    private val jsonObserver =Observer<SubscriptionJson>{subscriptionJson->
        if(subscriptionJson!=null){
                val purchases =subscriptionJson.listFromJsonString(subscriptionJson.purchaseJson)
                if(purchases!=null){
                    if(!purchases.acknowledged) {
                        mViewModel.acknowledgeToken(purchases.purchaseToken)
                    }
                    mViewModel.registerPurchases(purchases )
                }


            } }

    override fun proceed() {

    }


    public override fun onPause() {
        mInAppBillingDataBinding?.adsInclude?.apply {
            this.pause()
            println("Pause" + this)

        }
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        mInAppBillingDataBinding?.adsInclude?.apply {
            this.resume()

            println("Resume" + this)
        }

    }


    public override fun onDestroy() {
        mInAppBillingDataBinding?.adsInclude?.apply {
            this.destroy()
            println("onDestroy"+this)

        }

        super.onDestroy()
    }






















}



