package imagetrack.app.trackobject.ui.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.SubscriptionStatusDialogDataBinding
import imagetrack.app.trackobject.inapppurchaseUtils.SubscriptionNote
import imagetrack.app.trackobject.navigator.SubscriptionStatusNavigator
import imagetrack.app.trackobject.ui.activities.InAppPurchaseActivity
import imagetrack.app.trackobject.viewmodel.InAppViewModel


@AndroidEntryPoint
class SubscriptionStatusDialog : BaseDialogFragment<InAppViewModel, SubscriptionStatusDialogDataBinding>()  ,
    SubscriptionStatusNavigator, DialogInterface.OnDismissListener{

    override fun onDismiss(dialog: DialogInterface) {
        mInAppPurchaseActivity?.finish()

    }

    private val mViewModel by viewModels<InAppViewModel>()
    private var mBinding : SubscriptionStatusDialogDataBinding?=null
    private var mInAppPurchaseActivity : InAppPurchaseActivity?=null


    override fun getBindingVariable(): Int  =BR.viewModel
    override fun getViewModel(): InAppViewModel=mViewModel
    override fun getLayoutId(): Int =R.layout.subscription_status_dialog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = getViewDataBinding()

        /*** Attached Activity reference ***/
        val   baseActivity  = getBaseActivity()
        if(baseActivity is InAppPurchaseActivity){
            mInAppPurchaseActivity =baseActivity }


        dialog?.let {
            it.setCanceledOnTouchOutside(false)
        }
        val arguments_starting = arguments?.run {
            get(STARTING_TIME) }

        val arguments_expiry = arguments?.run {
            get(EXPIRY_TIME) }

        val arguments_orderId = arguments?.run {
            get(ORDER_ID) }

        mBinding?.apply {

            val startingText = arguments_starting as String
            val expiryText = arguments_expiry as String
             val orderText = arguments_orderId as String

            if(startingText.isNotEmpty()){
                startingId.text = "Starting Date : $startingText"
            }

            if(expiryText.isNotEmpty()){
                expiryTime.text = "Expiry Date : $expiryText" }

            if(orderText.isNotEmpty()){
                orderId.text ="Order Id : $orderText" }

        }?:throw NullPointerException("Scan Dialog Fragment   Data Binding is null")

        mViewModel.setNavigator(this)

    }



    companion object{

        private const val TAG : String="SubscriptionStatusDialog"
        private const val NO_TEXT_FOUND ="No Text found Try Again"

        @VisibleForTesting
        const val STARTING_TIME ="starting_time"
        const val EXPIRY_TIME ="expiry_time"
        const val ORDER_ID ="orderId"

        fun getInstance(subscriptionNote: SubscriptionNote): SubscriptionStatusDialog {
            val fragmentDialog = SubscriptionStatusDialog()
            val bundle = Bundle()
            bundle.putString(STARTING_TIME, subscriptionNote.startingDate)
            bundle.putString(EXPIRY_TIME ,subscriptionNote.ExpiryDate)
            bundle.putString(ORDER_ID , subscriptionNote.orderId)
            fragmentDialog.arguments = bundle
            return fragmentDialog
        }
    }

    override fun proceed() {
        openIntent()
    }

    private fun openIntent(){

        dismiss()
        mInAppPurchaseActivity?.finish()

        println("OpenIntent ")

    }

    fun showDialog(fragmentManager: FragmentManager) {
        super.showDialogs(fragmentManager ,TAG) }


}