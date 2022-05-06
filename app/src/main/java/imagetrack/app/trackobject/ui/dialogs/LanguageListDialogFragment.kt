package imagetrack.app.trackobject.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.lanuguages.BaseLanguageModel
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.ScanNavigatorDirections
import imagetrack.app.trackobject.adapter.LanguageAdapter
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.database.preferences.AdThreshold
import imagetrack.app.trackobject.databinding.LanguageListDataBinding
import imagetrack.app.trackobject.ext.fragmentRecycle
import imagetrack.app.trackobject.model.Ads
import imagetrack.app.trackobject.navgraph.NavGraph
import imagetrack.app.trackobject.navigator.LanguageListNavigator
import imagetrack.app.trackobject.ui.activities.MainActivity
import imagetrack.app.trackobject.viewmodel.LanguageListViewModel
import imagetrack.app.translate.TranslateUtils
import imagetrack.app.utils.InternetConnection
import imagetrack.app.utils.LanguageArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class LanguageListDialogFragment : BaseDialogFragment<LanguageListViewModel, LanguageListDataBinding>() ,OnItemClickListener<String> ,LanguageListNavigator  {

    private  var bind : LanguageListDataBinding?=null
    private val mViewModel by viewModels<LanguageListViewModel>()
    private  var resultText :Any?=null
    private var subscriptionStatus : SubscriptionStatus?=null
    private var adLoader : AdLoader?=null
    private var mRecyclerViewItems  : ArrayList<BaseLanguageModel>  =arrayListOf()
    private  var languageAdapter : LanguageAdapter?=null
    private var nativeArray : ArrayList<NativeAd> = arrayListOf()

    private var mainActivity :MainActivity? =null

   private  val args: LanguageListDialogFragmentArgs by navArgs()
   private var mNavController : NavController?=null


    companion object{

        private const val TAG :String= "LanguageListDialogaa"
        private const val KEY_VALUE ="textvalue"
        private var NUMBER_OF_ADS =3
}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind= getViewDataBinding()
        mNavController =    findNavController()
        println("Language List  Dialog Fragment" +mNavController)

        /*** Attached Activity reference ***/
       val   baseActivity  = getBaseActivity()
        if(baseActivity is MainActivity){
            mainActivity =baseActivity }

        /** Reference ViewMOdel with Activity **/
        mViewModel.setNavigator(this)

        /** Check Translation Avalibality **/
        checkTranslationAvailable()

        resultText =args.textvalue

       // resultText = arguments?.run { get(ScanDialogFragment.KEY_VALUE) }


        languageAdapter= LanguageAdapter( this)


        lifecycleScope.launch {
              val jobFetch =launch(Dispatchers.IO) {
              arrayItems() }
            jobFetch.join()

            withContext(Dispatchers.Main){
                updateRecyclerView()


            }

     }

//        mRecyclerViewItems.addAll(LanguageArray.arrayValues())
//        mRecyclerViewItems.add(0, Ads(null))
//        mRecyclerViewItems.add(40, Ads(null))
//        mRecyclerViewItems.add(80, Ads(null))
//        languageAdapter?.setData(mRecyclerViewItems)
        mViewModel.translate.observe(viewLifecycleOwner ,userObserver)

    }

    private fun updateRecyclerView(){
        bind?.languagelistId?.fragmentRecycle(requireContext(), languageAdapter!!)
        mainActivity?.let {
            if (!AdThreshold.getInstance(it).isMaxClickedPerformed()) {
                loadNativeAds() } } }


    private   fun arrayItems(){
        mRecyclerViewItems.addAll(LanguageArray.arrayValues())
        mRecyclerViewItems.add(0, Ads(null))
        mRecyclerViewItems.add(40, Ads(null))
        mRecyclerViewItems.add(80, Ads(null))
        languageAdapter?.setData(mRecyclerViewItems) }




    fun showDialog(fragmentManager: FragmentManager) {
        super.showDialogs(fragmentManager ,TAG)

    }

    override fun onDestroyView() {
        bind=null
        super.onDestroyView() }

    override fun onDestroy() {
        mRecyclerViewItems.clear()
        nativeArray.clear()
        languageAdapter = null
        super.onDestroy()
    }

    override fun getBindingVariable(): Int {
        return  BR.viewModel
    }

    override fun getViewModel(): LanguageListViewModel=mViewModel

    override fun getLayoutId(): Int {
        return  R.layout.language_list_dialog    }


     private fun startProgress() {
         bind?.progressId?.visibility=View.VISIBLE }

    private   fun stopProgress() {
         bind?.progressId?.visibility=View.GONE }

    override fun clickItem(item: String) {

//        val subscriptionStatus =  subscriptionStatus
//        val activity = mainActivity
//
//        if(activity!=null ) {
//            if (!InternetConnection.isInternetAvailable(activity)){
//                NavGraph.navigate(NavGraph.GLOBAL_INTERNET_CONNECTION,findNavController())
//                return } }
//
//        if(subscriptionStatus!=null){
//            val isExpire = subscriptionStatus.isExpired()
//            if(isExpire){
//                dismiss()
//                openIntent()
//            } else {

        val postParameters: MutableMap<String, String> = getLanguageApiData(item)
        startProgress()
        mViewModel.getUsersFlow(postParameters)

         //   .observe(this, userObserver)
//            }
//        }
//        else {
//            openIntent() }


    }

    private fun getLanguageApiData(item :String):MutableMap<String, String>{
        val postParameters: MutableMap<String, String> = HashMap()
        postParameters["q"] = resultText as String
        postParameters["target"] =item
        println("Result is given"+item)

        postParameters["key"] = TranslateUtils.SCANNER_KEY
        return postParameters
    }



  private   val userObserver = Observer<String?> {
        stopProgress()
        if (it != null) {
            NavGraph.navigate(NavGraph.LIST_DIALOG_TO_SCAN_DIALOG ,findNavController() ,it)

        }
  }

    override fun close() {
        this.dismiss() }

    //Subscription
    private fun openIntent(){
        NavGraph.navigate(NavGraph.LIST_DiALOG_TO_INAPP ,findNavController())
    }

    private fun checkTranslationAvailable(){
        mViewModel.subscriptionLiveData.observe(this, {
            subscriptionStatus = it

        })
    }


    private fun  insertAdsInMenuItems(){
        val size =nativeArray.size
        if(size<=0 || size >3) {
            return }

        var adStep = 0
        for(ad  in 0 until size step 1 ){
            val languageModel =    mRecyclerViewItems[adStep]
            val nativeAds =   nativeArray[ad]

            if(languageModel is Ads){
                languageModel.nativeAds=nativeAds

             languageAdapter?.notifyItemChanged(adStep)

            }

           adStep+=40

        }

    }

    private fun loadNativeAds(){
        if(nativeArray.isNotEmpty()) {
            nativeArray.clear() }



         adLoader = AdLoader.Builder(requireActivity(), resources.getString(R.string.subscription_native))
            .forNativeAd { ad: NativeAd ->

                nativeArray.add(ad)
               val  loader =adLoader
                if (loader !=null && !loader.isLoading) {
                    insertAdsInMenuItems() } }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {

                    println("Loading Failed code  " + adError.code)
                    println("Loading failed message " + adError.message)


                    // Handle the failure by logging, altering the UI, and so on.


                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    // Methods in the NativeAdOptions.Builder class can be
                    // used here to specify individual options settings.
                    .build()
            ).withAdListener(object : AdListener(){

                 override fun onAdClicked() {
                     super.onAdClicked()

                     mainActivity?.let {
                         lifecycleScope.launch(Dispatchers.IO){
                             AdThreshold.getInstance(it).save(1) }
                     }
                 }
             })


            .build()




        adLoader?.loadAds(AdRequest.Builder().build(),NUMBER_OF_ADS)



    }




}
