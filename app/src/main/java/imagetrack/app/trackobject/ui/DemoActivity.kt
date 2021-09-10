package imagetrack.app.trackobject.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.local.inappdatabase.SubscriptionApi
import imagetrack.app.trackobject.databinding.DemoDataBinding
import imagetrack.app.trackobject.storage.Storage
import imagetrack.app.trackobject.ui.activities.BaseActivity
import imagetrack.app.trackobject.viewmodel.InAppViewModel
import javax.inject.Inject


@AndroidEntryPoint
class DemoActivity : BaseActivity<InAppViewModel, DemoDataBinding>() {


    @Inject
    lateinit var subscriptionApi : SubscriptionApi


    val storage = Storage(this)


    private var mLiveFragmentDataBinding: DemoDataBinding? = null
    private val mViewModel by viewModels<InAppViewModel>()
    override fun getBindingVariable(): Int  =BR.viewModel
    override fun getLayoutId(): Int  = R.layout.demo
    override fun getViewModel(): InAppViewModel = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLiveFragmentDataBinding = getViewDataBinding()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



    }



   private  fun toast(name: String){
        Toast.makeText(this, name, Toast.LENGTH_LONG).show() }


    companion object {
        private const val TAG = "ServerImpl"

        private const val SKU_KEY = "sku"
        private const val PURCHASE_TOKEN_KEY = "token"
        private const val BASIC_CONTENT_CALLABLE = "content_basic"
        private const val PREMIUM_CONTENT_CALLABLE = "content_premium"
        private const val SUBSCRIPTION_STATUS_CALLABLE = "subscription_status"
        private const val REGISTER_SUBSCRIPTION_CALLABLE = "subscription_register"
        private const val TRANSFER_SUBSCRIPTION_CALLABLE = "subscription_transfer"
        private const val REGISTER_INSTANCE_ID_CALLABLE = "instanceId_register"
        private const val UNREGISTER_INSTANCE_ID_CALLABLE = "instanceId_unregister"

            private const val RC_SIGN_IN = 9001

    }












}
