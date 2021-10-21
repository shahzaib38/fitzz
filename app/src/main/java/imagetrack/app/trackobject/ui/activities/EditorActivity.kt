package imagetrack.app.trackobject.ui.activities

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ClipBoardManager
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.EditorDataBinding
import imagetrack.app.trackobject.ext.showLanguageList
import imagetrack.app.trackobject.viewmodel.EditorViewModel
import imagetrack.app.trackobject.navigator.EditorNavigator


@AndroidEntryPoint
class EditorActivity  :   BaseActivity<EditorViewModel, EditorDataBinding>()   ,
    EditorNavigator {


    private val mViewModel by viewModels<EditorViewModel>()

    private  var mBinding  : EditorDataBinding?=null

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.editor_activity

    override fun getViewModel(): EditorViewModel =mViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = getViewDataBinding()

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN)


        val data = intent.getStringExtra(resources.getString(R.string.edit_value))

            mBinding?.editorId?.let {
                if(data!=null && data.isNotEmpty()){

                    it.setText(data)

                }

            }




        mViewModel.setNavigator(this)


    }





    override fun copy() {
        ClipBoardManager.clipInstance(this).copy(getText())
        toast("Copied") }

    override fun edit() {}

    private fun getText():String{
        return mBinding?.editorId?.text?.toString() ?: "Try again" }


    private fun  toast(value: String){
        Toast.makeText(this, value, Toast.LENGTH_LONG).show() }

    override fun share() {
        toast("Sharing")
        super.shareData(getText())
    }

    override fun backPress() {
        finish() }


    override fun translate() {
        this?.showLanguageList(getText())

     //   LanguageListDialogFragment.getInstance(getText()).showDialog()
    }




}