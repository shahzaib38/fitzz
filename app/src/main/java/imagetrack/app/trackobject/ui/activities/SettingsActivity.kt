package imagetrack.app.trackobject.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.adapter.SettingsAdapter
import imagetrack.app.trackobject.common.SettingsUtils
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.databinding.SettingsDataBinding
import imagetrack.app.trackobject.ext.*
import imagetrack.app.trackobject.inapppurchaseUtils.Constants.SUBSCRIPTION_EXPIRE
import imagetrack.app.trackobject.inapppurchaseUtils.Constants.SUBSCRIPTION_NOT_PURCHASE
import imagetrack.app.trackobject.inapppurchaseUtils.createTimeNote
import imagetrack.app.trackobject.ui.dialogs.SubscriptionDetailDialog
import imagetrack.app.trackobject.viewmodel.SettingsViewModel
import imagetrack.app.utils.InternetConnection


@AndroidEntryPoint
class SettingsActivity : BaseActivity<SettingsViewModel, SettingsDataBinding>() , OnItemClickListener<String> {

    private val mViewModel by viewModels<SettingsViewModel>()
    private var mSettingsDataBinding : SettingsDataBinding?=null
    private var subscriptionStatus : SubscriptionStatus?=null
    private var APPLINK ="https://play.google.com/store/apps/details?id=imagetrack.app.trackobject"



    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int  = R.layout.settings_layout

    override fun getViewModel(): SettingsViewModel = mViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mSettingsDataBinding = getViewDataBinding()

        loadSettingData()

    setupAds()
    }


    private fun  setupAds(){

        mSettingsDataBinding?.adsInclude?.apply {

            val unitId=    resources.getString(R.string.settings_native)

                adsId.ads(this@SettingsActivity, unitId, advertiseId)

    }

    }

    private fun loadSettingData(){
        val settingAdapter =SettingsAdapter(this)
        settingAdapter.setData(SettingsUtils.arrayList())

        mSettingsDataBinding?.settingsId?.recycle(this, settingAdapter)

        checkAvailability()


    }

    override fun clickItem(item: String) {

        when(item){
            SettingsUtils.HISTORY -> {
                this.launchActivity(HistoryActivity::class.java)
            }

            SettingsUtils.VIEW_PDF -> {
                this.launchActivity(PdfActivity::class.java)
            }

            SettingsUtils.PURCHASE_REPORT -> {
                checkSubscription()


            }

            SettingsUtils.SHARE_APP -> {

                shareData(APPLINK)

            }
//            SettingsUtils.CONTACT_DEVELOPER -> {
//
//                openEmail()
//
//            }

        }


    }


    private fun openEmail(){

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(SettingsUtils.EMAIL))
        startActivity(Intent.createChooser(intent, ""))


    }



    private fun checkAvailability(){
        mViewModel.subscriptionLiveData.observe(this, Observer<SubscriptionStatus> {
            subscriptionStatus = it

        })
    }







    private fun checkSubscription(){
        if(!InternetConnection.isInternetAvailable(this))
        { this.internetConnectionDialog()
            return }

        val mSubscriptionStatus = subscriptionStatus
            if(mSubscriptionStatus!=null){
                val isExpire = mSubscriptionStatus.isExpired()
                if(isExpire){

                    toast(SUBSCRIPTION_EXPIRE)

                } else {
                    if(mSubscriptionStatus!=null) {
                        val subscriptionNote = createTimeNote(mSubscriptionStatus)
                        if (!mSubscriptionStatus.isExpired()) {
                            SubscriptionDetailDialog.getInstance(subscriptionNote).showDialog()
                        } }
                }
            }
            else {
                toast(SUBSCRIPTION_NOT_PURCHASE)

            }


    }

}