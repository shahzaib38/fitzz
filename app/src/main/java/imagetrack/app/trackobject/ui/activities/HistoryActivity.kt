package imagetrack.app.trackobject.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.recyclerview.VerticalItemDecorator
import imagetrack.app.trackobject.adapter.HistoryAdapter
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.databinding.HistoryDataBinding
import imagetrack.app.trackobject.navigator.HistoryNavigator
import imagetrack.app.trackobject.viewmodel.HistoryViewModel
import java.util.ArrayList


@AndroidEntryPoint
class HistoryActivity : BaseActivity<HistoryViewModel, HistoryDataBinding>() , HistoryNavigator , OnItemClickListener<HistoryBean> {

    private val mViewModel by viewModels<HistoryViewModel>()
    private var mHistoryDataBinding : HistoryDataBinding?=null
    private  var mHistoryAdapter : HistoryAdapter?=null


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int  = R.layout.history_layout

    override fun getViewModel(): HistoryViewModel  = mViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mHistoryDataBinding = getViewDataBinding()

        mViewModel.setNavigator(this)
        mHistoryAdapter= HistoryAdapter(this)

        setUpRecyclerView()
    }


    private    fun setUpRecyclerView(){
        mViewModel.getAllHistoryData().observe(this , Observer {
            mHistoryAdapter?.setData(it as ArrayList) })

        val  mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mHistoryDataBinding?.historyRecyclerview?.apply {
            layoutManager =mLayoutManager
            this.addItemDecoration(VerticalItemDecorator())
            adapter = mHistoryAdapter }


    }

    override fun notifyDataBase() {
        mHistoryAdapter?.notifyDataSetChanged() }

    override fun clickItem(item: HistoryBean) {


    }

}