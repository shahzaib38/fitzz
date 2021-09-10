package imagetrack.app.trackobject.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.adapter.HistoryAdapter
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.databinding.HistoryDataBinding
//import imagetrack.app.trackobject.ext.ads
import imagetrack.app.trackobject.ext.recycle
import imagetrack.app.trackobject.navigator.HistoryNavigator
import imagetrack.app.trackobject.ui.dialogs.InternetConnectionDialog
import imagetrack.app.trackobject.ui.dialogs.LanguageListDialogFragment
import imagetrack.app.trackobject.viewmodel.HistoryViewModel
import imagetrack.app.utils.InternetConnection
import kotlinx.android.synthetic.main.ads_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

interface  HistoryListener{
    fun hide()
    fun show()
    fun share(data :String)
    fun translate(data :String)
}


@AndroidEntryPoint
class HistoryActivity : BaseActivity<HistoryViewModel, HistoryDataBinding>() , HistoryNavigator , HistoryListener {

    private val mViewModel by viewModels<HistoryViewModel>()
    private var mHistoryDataBinding : HistoryDataBinding?=null
    private  var mHistoryAdapter : HistoryAdapter?=null
    private var subscriptionStatus : SubscriptionStatus?=null


    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int  = R.layout.history_layout
    override fun getViewModel(): HistoryViewModel  = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mHistoryDataBinding = getViewDataBinding()
        mViewModel.setNavigator(this)
        mHistoryAdapter= HistoryAdapter(this)
        setUpRecyclerView()
        //checkTranslationAvailable()


      //  setupAds()
    }



    private    fun setUpRecyclerView(){
        mViewModel.getAllHistoryData().observe(this, Observer { mHistoryAdapter?.setData(it as ArrayList) })
            mHistoryDataBinding?.historyRecyclerview?.recycle(this ,mHistoryAdapter)
    }

    override fun notifyDataBase() {
        mHistoryAdapter?.notifyDataSetChanged() }

    override fun deleteAll() {
      mHistoryAdapter?.let {
         it.clearAll()
          it.notifyDataSetChanged()
          toast("History Deleted")
      }
    }



    private fun toast(name :String){
        Toast.makeText(this ,name ,Toast.LENGTH_LONG).show()
    }

    override fun hide() {
        mHistoryDataBinding?.let {
            it.historyRecyclerview.visibility =View.GONE
            it.emptyId.visibility=View.VISIBLE
        }
    }

    override fun show() {
        mHistoryDataBinding?.let {

            it.historyRecyclerview.visibility = View.VISIBLE
            it.emptyId.visibility=View.GONE

        }
    }

    override fun share(data: String) {
        toast("Sharing")
        super.shareData(data)
    }


    private fun openIntent(){
        val intent = Intent(this ,InAppPurchaseActivity::class.java)
        startActivity(intent) }


    override fun translate(data: String) {

     //   val subscriptionStatus =  subscriptionStatus

        if(!InternetConnection.isInternetAvailable(this)) {
            InternetConnectionDialog.getInstance().showDialog(supportFragmentManager)
            return }


//        if(subscriptionStatus!=null){
//            val isExpire = subscriptionStatus.isExpired()
//            if(isExpire){
//
//                openIntent()
//            }
//
//            else {
                LanguageListDialogFragment.getInstance(data).showDialog(supportFragmentManager)
//
//            }
//        }
//        else {
//            openIntent() }

    }

//    private fun checkTranslationAvailable(){
//        mViewModel.subscriptionLiveData.observe(this , Observer<SubscriptionStatus>{
//            subscriptionStatus =  it
//
//
//
//
//
//
//        })
//
//
//    }


}