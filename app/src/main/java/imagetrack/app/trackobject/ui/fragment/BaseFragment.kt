package imagetrack.app.trackobject.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import imagetrack.app.trackobject.ui.activities.BaseActivity


abstract class BaseFragment<VM : ViewModel, VDB: ViewDataBinding> : Fragment() {


    private var mActivity: BaseActivity<*, *>? = null
    private var mViewModel :VM? =null
    private var mViewDataBinding :VDB?=null

    abstract fun  getBindingVariable() :Int

    @LayoutRes
    abstract fun getLayoutId() :Int

    abstract fun getViewModel() :VM



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding=  DataBindingUtil.inflate(inflater,getLayoutId(),container, false)
        mViewDataBinding?.run {
            println("OnViewCreated")

            setVariable(getBindingVariable(), mViewModel)
            lifecycleOwner = this@BaseFragment
            executePendingBindings()

        }
        return mViewDataBinding?.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel=getViewModel()

        println("OnCreate")

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is BaseActivity<*, *>){
            mActivity =context
        }
    }


    fun  getBaseActivity(): BaseActivity<*, *>?{
        return mActivity
    }








    fun getViewDataBinding(): VDB? {
        return mViewDataBinding
    }


}