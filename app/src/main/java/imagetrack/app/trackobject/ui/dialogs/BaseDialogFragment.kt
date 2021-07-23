package imagetrack.app.trackobject.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.ui.activities.BaseActivity

val TAG = BaseDialogFragment::class.simpleName

abstract class BaseDialogFragment<VM : ViewModel,VDB : ViewDataBinding> : DialogFragment() {


    private var mActivity: BaseActivity<*, *>? = null
    private var mViewDataBinding :VDB?=null
    private var mRootView: View? = null
    private  var mViewModel :VM? =null


    abstract fun getBindingVariable(): Int
    abstract fun getViewModel(): VM?
    @LayoutRes
    abstract fun getLayoutId(): Int

    fun getViewDataBinding(): VDB? {
        return mViewDataBinding
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding=  DataBindingUtil.inflate(inflater,getLayoutId(),container, false)
        mViewDataBinding?.run {
            println("OnViewCreated")
            setVariable(getBindingVariable(), mViewModel)
            lifecycleOwner = this@BaseDialogFragment
            executePendingBindings() }
        return mViewDataBinding?.root }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel=getViewModel()


    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        val dialog = Dialog(requireContext(),R.style.PauseDialog)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)


        if (dialog != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        }

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is BaseActivity<*,*>){
            mActivity =context
//            getBaseActivity()?.onFragmentAttached()
        }
    }



    fun  getBaseActivity():BaseActivity<*,*>?{
        return mActivity
    }


    open fun showDialog(fragment : FragmentManager){

        if (fragment!=null) {
            val fragmentTransaction =
                fragment.beginTransaction()
            val fragmentByTag = fragment.findFragmentByTag(tag)
            if (fragmentByTag != null) {
                fragmentTransaction.remove(fragmentByTag)
            }
            fragmentTransaction.addToBackStack(null)
            show(fragment, TAG)
        }

    }


    open    fun dismissDialog(tag :String?){

        dismiss()

//        getBaseActivity()?.onFragmentDetached(tag)

    }



    fun shareData(value :String ="no value was shared"){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, value)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        requireActivity().startActivity(shareIntent)
    }



    open fun restartAnActivity(intent  : Intent){
        requireActivity().finish()
        startActivity(intent)

    }

}