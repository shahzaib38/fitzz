package imagetrack.app.trackobject.ui.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.adapter.HistoryAdapter
import imagetrack.app.trackobject.database.preferences.AdThreshold
import imagetrack.app.trackobject.databinding.HistoryDataBinding
import imagetrack.app.trackobject.ext.recycle
//import imagetrack.app.trackobject.ext.showLanguageList
import imagetrack.app.trackobject.navigator.HistoryNavigator
import imagetrack.app.trackobject.viewmodel.HistoryViewModel
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


    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int  = R.layout.history_layout
    override fun getViewModel(): HistoryViewModel  = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mHistoryDataBinding = getViewDataBinding()
        mViewModel.setNavigator(this)
        mHistoryAdapter= HistoryAdapter(this)
      //  setUpRecyclerView()

        if(!AdThreshold.getInstance(this).isMaxClickedPerformed()) {
            setupAds() }


        setUpRecyclerView()

    }

    private fun  setupAds(){
        mHistoryDataBinding?.adsInclude?.apply {
            val adRequest = AdRequest.Builder().build()
            this.loadAd(adRequest)
            this.adListener =object : AdListener(){

                override fun onAdClicked() {
                    super.onAdClicked()
                    lifecycleScope.launch(Dispatchers.IO){
                        AdThreshold.getInstance(this@HistoryActivity).save(1) }


                }

            }
        }
    }

    private fun setUpRecyclerView(){
        mViewModel.state.observe(this, Observer {

            println("History "+ Thread.currentThread().name)
            mHistoryAdapter?.setData(it as ArrayList)


        })


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

    override fun translate(data: String) {
//        this.showLanguageList(data)
    }


    // Called when leaving the activity
    public override fun onPause() {
        mHistoryDataBinding?.adsInclude?.apply {
            this.pause()
            println("Pause" + this)

        }
        super.onPause()
    }

    // Called when returning to the activity
    public override fun onResume() {
        super.onResume()
        mHistoryDataBinding?.adsInclude?.apply {
            this.resume()

            println("Resume" + this)
        }

    }

    // Called before the activity is destroyed
    public override fun onDestroy() {
        mHistoryDataBinding?.adsInclude?.apply {
            this.destroy()
            println("onDestroy"+this)

        }

        super.onDestroy()
    }





}