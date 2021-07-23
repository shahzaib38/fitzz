package imagetrack.app.trackobject.ui.dialogs

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.ClipBoardManager
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.databinding.ScanDialogDataBinding
import imagetrack.app.trackobject.inapppurchaseUtils.createTimeNote
import imagetrack.app.trackobject.navigator.ScanDialogNavigator
import imagetrack.app.trackobject.ui.activities.EditorActivity
import imagetrack.app.trackobject.ui.activities.InAppPurchaseActivity
import imagetrack.app.trackobject.viewmodel.ScanDialogViewModel
import imagetrack.app.utils.DateUtils
import imagetrack.app.utils.InternetConnection


@AndroidEntryPoint
 class ScanDialogFragment : BaseDialogFragment<ScanDialogViewModel, ScanDialogDataBinding>()  , ScanDialogNavigator , DialogInterface.OnDismissListener  {


    override fun onDismiss(dialog: DialogInterface) {}
    private val mViewModel by viewModels<ScanDialogViewModel>()
    private var mBinding  :ScanDialogDataBinding? =null
    private var subscriptionStatus : SubscriptionStatus?=null

    override fun getBindingVariable(): Int =BR.viewModel
    override fun getViewModel(): ScanDialogViewModel = mViewModel
    override fun getLayoutId(): Int  = R.layout.translated_text

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = getViewDataBinding()
        mViewModel.setNavigator(this)
        dialog?.setCanceledOnTouchOutside(false)

        val arguments = arguments?.run { get(KEY_VALUE) }

        mBinding?.apply {
            val derivedText =arguments as? String
            this.translatedtext.translateText(derivedText) }

        checkTranslationAvailable()

        mBinding?.viewOrderId?.setOnClickListener {
            val mSubscriptionStatus =subscriptionStatus

            if(mSubscriptionStatus!=null) {
                val subscriptionNote = createTimeNote(mSubscriptionStatus)
                if (!mSubscriptionStatus.isExpired()) {
                    SubscriptionDetailDialog.getInstance(subscriptionNote).showDialog(this.parentFragmentManager)
                }

            }

        }

    }

    private  fun EditText.translateText(derivedText :String?){
        val isTextNotNullAndEmpty = derivedText !=null && derivedText.isNotEmpty()

        if (isTextNotNullAndEmpty) {
            setText(derivedText)
        } else {
            setText(NO_TEXT_FOUND)
        } }





    private fun openIntent(){
        requireActivity().finish()
        val intent = Intent(requireActivity() ,InAppPurchaseActivity::class.java)
        requireActivity().startActivity(intent) }




        companion object{
        private const val TAG : String="ScanDialogFragment"
         const val NO_TEXT_FOUND ="No Text found Try Again"

            @VisibleForTesting
             const val KEY_VALUE ="textvalue"

        fun getInstance(text: String): ScanDialogFragment {
            val fragmentDialog = ScanDialogFragment()
            val bundle = Bundle()
            bundle.putString(KEY_VALUE, text)

            fragmentDialog.arguments = bundle
            return fragmentDialog
            }
    }


    override fun dismissDialog() {}

    override fun copy() {
        ClipBoardManager.clipInstance(requireContext()).copy(getText())
               toast("Copied") }

    override fun edit() {

        val intent = Intent(context ,EditorActivity::class.java)
        intent.putExtra(resources.getString(R.string.edit_value),getText())
        startActivity(intent)
       this.dismiss()
    }

    private fun getText():String{
        return mBinding?.translatedtext?.text?.toString() ?: "" }


  private fun  toast(value: String){
       Toast.makeText(requireContext(), value, Toast.LENGTH_LONG).show() }

    override fun share() {
        toast("Sharing")
        super.shareData(getText())
    }

        override fun pdf() {
        PdfCreatorDialog.getInstance(getText()).showDialog(childFragmentManager) }

    private fun checkTranslationAvailable(){
        mViewModel.subscriptionLiveData.observe(this , Observer<SubscriptionStatus>{
            subscriptionStatus =  it

            if(it!=null){
                val isExpire = it.isExpired()
                if(isExpire){
                    invisibleOrder()
                }

                else {

                    displayOrder()

                }
            }
            else {
                invisibleOrder()

            }





        })


    }


   private fun displayOrder(){
        mBinding?.viewOrderId?.visibility =View.VISIBLE }

    private fun invisibleOrder(){
        mBinding?.viewOrderId?.visibility =View.INVISIBLE }




    override fun translate() {
      val subscriptionStatus =  subscriptionStatus

        if(!InternetConnection.isInternetAvailable(requireActivity()))
        {
            InternetConnectionDialog.getInstance().showDialog(parentFragmentManager)
            return
        }


        if(subscriptionStatus!=null){
            val isExpire = subscriptionStatus.isExpired()
            if(isExpire){
                openIntent()
                invisibleOrder()
            }

            else {
                LanguageListDialogFragment.getInstance(getText()).showDialog(parentFragmentManager)
                dismiss()
                displayOrder()

            }
        }
        else {
        invisibleOrder()
            openIntent() }

    }

    override fun startProgress() {}

    override fun stopProgress() {}

    override fun exit() {
          saveToDataBase()
        this.dismiss() }




    private fun  saveToDataBase(){

        if(getText().isEmpty())return

        try{
       mViewModel.insertHistory(HistoryBean(value =getText(),date = DateUtils.getTime().toString()))
        } catch (e: Exception){

            val mess =e.message
            if(mess!=null) {
                toast(mess)
            }

            val isShowing =dialog?.isShowing
            if(isShowing!=null && isShowing ) {
             toast("Showing ")
                this.dismiss()
            }else{
                toast("Not Showing")
            }

        }
    }




}