package imagetrack.app.trackobject.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.android.billingclient.api.Purchase
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.InAppBillingDataBinding
import imagetrack.app.trackobject.databinding.MainDataBinding
import imagetrack.app.trackobject.in_app_purchase.Constants
import imagetrack.app.trackobject.viewmodel.InAppViewModel

@AndroidEntryPoint
class InAppPurchaseActivity : BaseActivity<InAppViewModel, InAppBillingDataBinding>() {

    private val mViewModel by viewModels<InAppViewModel>()

    private var mMainDataBinding: InAppBillingDataBinding? = null

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.in_app_billing_layout
    override fun getViewModel(): InAppViewModel = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mMainDataBinding = getViewDataBinding()


        lifecycle.addObserver(mViewModel.billingLifecycle)
        mViewModel.buyEvent.observe(this, Observer {
            it?.let {
                mViewModel.billingLifecycle.launchBillingFlow(this, it)
            }
        })

        mViewModel.openPlayStoreSubscriptionsEvent.observe(this, Observer {
          //  Log.i(TAG, "Viewing subscriptions on the Google Play Store")
            val sku = it
            val url = if (sku == null) {
                // If the SKU is not specified, just open the Google Play subscriptions URL.
                Constants.PLAY_STORE_SUBSCRIPTION_URL
            } else {
                // If the SKU is specified, open the deeplink for this SKU on Google Play.
                String.format(Constants.PLAY_STORE_SUBSCRIPTION_DEEPLINK_URL, sku, packageName)
            }
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        })

    }


}