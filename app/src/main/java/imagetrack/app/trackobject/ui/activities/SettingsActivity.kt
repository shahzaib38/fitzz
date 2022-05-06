package imagetrack.app.trackobject.ui.activities

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.NetworkRequest
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.datatransport.runtime.scheduling.jobscheduling.JobInfoScheduler
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.Workmanager.CopyToTranslateWorkManager
import imagetrack.app.trackobject.adapter.SettingsAdapter
import imagetrack.app.trackobject.common.SettingsUtils
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.database.preferences.AdThreshold
import imagetrack.app.trackobject.databinding.SettingsDataBinding
import imagetrack.app.trackobject.ext.*
import imagetrack.app.trackobject.inapppurchaseUtils.Constants.SUBSCRIPTION_EXPIRE
import imagetrack.app.trackobject.inapppurchaseUtils.Constants.SUBSCRIPTION_NOT_PURCHASE
import imagetrack.app.trackobject.inapppurchaseUtils.createTimeNote
import imagetrack.app.trackobject.services.JobSchedularIntent
import imagetrack.app.trackobject.services.TranslateAccessibilitService
import imagetrack.app.trackobject.viewmodel.SettingsViewModel
import imagetrack.app.utils.InternetConnection


@AndroidEntryPoint
class SettingsActivity : BaseActivity<SettingsViewModel, SettingsDataBinding>() , OnItemClickListener<String> , FragmentManager.OnBackStackChangedListener  {

    private val mViewModel by viewModels<SettingsViewModel>()
    private var mSettingsDataBinding : SettingsDataBinding?=null
    private var subscriptionStatus : SubscriptionStatus?=null
    private var APPLINK ="https://play.google.com/store/apps/details?id=imagetrack.app.trackobject"

    override fun onBackStackChanged() {
    }



    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int  = R.layout.settings_layout

    override fun getViewModel(): SettingsViewModel = mViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mSettingsDataBinding = getViewDataBinding()


        loadSettingData()



        if(!AdThreshold.getInstance(this).isMaxClickedPerformed()) {
            setupAds() }


    }


    private fun  setupAds(){
        mSettingsDataBinding?.adsInclude?.apply {
            val unitId=    resources.getString(R.string.settings_native)
                adsId.ads(lifecycleScope,this@SettingsActivity, unitId, advertiseId) }
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
            SettingsUtils.ENABLE_COPY_TO_TRANSLATE -> {

                startWorker()

            }

        }


    }


    private fun startWorker() {
        startActivity(Intent(this ,
            TranslateAccessibilityActivity::class.java))

//        var JOB_ID =1212
//
//      var   jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE)as JobScheduler
//        val componentName = ComponentName(this, JobSchedularIntent::class.java)
//    var    builder = JobInfo.Builder(JOB_ID, componentName)
//           builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_CELLULAR)
//        jobScheduler.schedule(builder.build())


    }


    private fun openEmail(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(SettingsUtils.EMAIL))
        startActivity(Intent.createChooser(intent, ""))
    }



    private fun checkAvailability(){
        mViewModel.subscriptionLiveData.observe(this, Observer<SubscriptionStatus> {
            subscriptionStatus = it }) }

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
                            this.subscriptionDetailDialog(subscriptionNote)
                        } }
                }
            }
            else {
                toast(SUBSCRIPTION_NOT_PURCHASE)

            }


    }

}