package imagetrack.app.trackobject.ui.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ClipBoardManager
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.ads.InterstitialAds
import imagetrack.app.trackobject.ads.NativAdAdvance
import imagetrack.app.trackobject.databinding.EditorDataBinding
import imagetrack.app.trackobject.viewmodel.EditorViewModel
import imagetrack.app.trackobject.ads.OnAdVisibilityListener
import imagetrack.app.trackobject.navigator.EditorNavigator
import imagetrack.app.trackobject.ui.dialogs.LanguageListDialogFragment


@AndroidEntryPoint
class EditorActivity  :   BaseActivity<EditorViewModel, EditorDataBinding>()  ,OnAdVisibilityListener ,
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
        InterstitialAds.load(this,resources.getString(R.string.full_screen_exit))

        mBinding?.let {
            NativAdAdvance.loadAd(this, it ,this,resources.getString(R.string.scan_editor_unitId)) }

    }

    override fun showAd() {
        mBinding?.template?.visibility = View.VISIBLE
    }

    override fun hideAd() {
        mBinding?.template?.visibility = View.GONE
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
        InterstitialAds.show(this)
        finish()
    }


    override fun translate() {
        LanguageListDialogFragment.getInstance(
            getText()
        ).showDialog(supportFragmentManager)


    }




}