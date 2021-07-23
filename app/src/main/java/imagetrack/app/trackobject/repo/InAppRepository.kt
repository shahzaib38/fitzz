package imagetrack.app.trackobject.repo


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.GoogleCredentials
import imagetrack.app.trackobject.database.local.ILocalDataSource
import imagetrack.app.trackobject.database.local.LocalDataSource
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.database.local.inappdatabase.SubscriptionApi
import imagetrack.app.trackobject.enum.ProgressStatus
import imagetrack.app.trackobject.inapppurchaseUtils.BillingClientLifecycle
import imagetrack.app.trackobject.inapppurchaseUtils.ContentResource
import imagetrack.app.trackobject.inapppurchaseUtils.purchaseForSku
import imagetrack.app.trackobject.token_loader.TokenLoader
import imagetrack.app.trackobject.ui.activities.InAppPurchaseActivity
import imagetrack.app.utils.DateUtils
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class InAppRepository @Inject constructor(
    private val localDataSource: ILocalDataSource,
   val billingClientLifecycle: BillingClientLifecycle,
    private val tokenLoader  : TokenLoader
    ) :BaseRepository() {








    /**
     * Live data is true when there are pending network requests.
     */
    var loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)


    @Inject
    lateinit var subscriptionApi: SubscriptionApi
    val skuDetails  = billingClientLifecycle.getSkuWithSkuDetailsLiveData()
    val purchaseLiveData = billingClientLifecycle.getPurchaseLiveData()
    val purchaseUpdateLiveData =billingClientLifecycle.getPurchaseUpdateEventLiveData()



    val purchaseJsonLiveData = localDataSource.getPurchaseJsonLiveData()

    val subscriptionLiveData = localDataSource.getSubscriptionJsonLiveData()

    val accessDeniedLiveData : MutableLiveData<String> = MutableLiveData<String>()


    /**
     * [MediatorLiveData] to coordinate updates from the database and the network.
     *
     * The mediator observes multiple sources. The database source is immediately exposed.
     * The network source is stored in the database, which will eventually be exposed.
     * The mediator provides an easy way for us to use LiveData for both the local data source
     * and the network data source, without implementing a new callback interface.
     */
    val subscriptions = MediatorLiveData<List<SubscriptionStatus>>()

    /**
     * Live Data with the basic content.
     */
    val basicContent = MediatorLiveData<ContentResource>()

    /**
     * Live Data with the premium content.
     */
    val premiumContent = MediatorLiveData<ContentResource>()


    /**
     * Delete local user data when the user signs out.
     */
    fun deleteLocalUserData() {
       // localDataSource.deleteLocalUserData()
        basicContent.postValue(null)
        premiumContent.postValue(null)
    }

    companion object {

        @Volatile
        private var INSTANCE: InAppRepository? = null

        fun getInstance(
            localDataSource: LocalDataSource,
            billingClientLifecycle: BillingClientLifecycle , tokenLoader : TokenLoader
        ): InAppRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?:
                InAppRepository(localDataSource, billingClientLifecycle ,tokenLoader)
                    .also { INSTANCE = it }
            }
    }




    fun insertPurchase(purchase :Purchase){
        localDataSource.updatePurchaseSubscriptions(purchase.originalJson) }

     fun acknowledgeRegisteredPurchaseTokens(
        purchaseToken :String)
     { billingClientLifecycle.acknowledgePurchase(purchaseToken)}

    fun launchBillinFlow(
        inAppPurchaseActivity: InAppPurchaseActivity,
        billingFlowParams: BillingFlowParams){
        billingClientLifecycle.launchBillingFlow(inAppPurchaseActivity,billingFlowParams)
    }



   suspend fun purchaseInit(purchaseList : List<Purchase>){

    val purchase =   purchaseForSku(purchaseList)
       if(purchase!=null) {
            loading.postValue(true)
           processPurchaseForSubscriptionJson(purchase)

       }
   }

    // is Local DataBase have Subscription json
    //if Yes check is Token Already Alvailable Update Purchase Json Table
    //if No Convert json into Object and pass and register it to Subscription Table
    //If Subscription is Not Avaaible then update Purchase Json

   suspend fun  processPurchaseForSubscriptionJson(purchase : Purchase){
            val subscriptionJson = localDataSource.getPurchaseJson()
        if(subscriptionJson!=null){

            if(!purchase.isAcknowledged){
                billingClientLifecycle.acknowledgePurchase(purchase.purchaseToken) }

            registerPurchaseToLocalDataBase(purchase)

        }else {
            loading.postValue(false)
            localDataSource.updatePurchaseSubscriptions(purchase.originalJson)

        }


   }


    suspend fun registerLocalPurchaseToLocalDataBase(purchase:imagetrack.app.trackobject.database.local.inappdatabase.Purchase ) {
        println("Register Purchase ")
        val sku = purchase.productId
        val purchaseToken = purchase.purchaseToken
        val packageName =purchase.packageName
       val accessToken = tokenLoader.getAccessToken()

        println("Local Refresh Token $accessToken")
        println("Local Sku $sku")
        println("Local Purchase Token  $purchaseToken")
        println("Local Package Name $packageName")
        if(accessToken.isEmpty()){


        }else {

            val subscriptionStatus =
                subscriptionApi.getSubscriptionDetails(packageName, sku, purchaseToken, accessToken)
            println("Original Exprie Time " + DateUtils.getDateTime(subscriptionStatus.expiryTimeMillis.toLong()))

            localDataSource.updateSubscriptions(subscriptionStatus)

        }
    }


     suspend fun registerPurchaseToLocalDataBase(purchase:Purchase ) {


        println("Register Purchase ")
         val sku = purchase.skus[0]
        val purchaseToken = purchase.purchaseToken
        val packageName =purchase.packageName

         val accessToken = tokenLoader.getAccessToken()
         loading.postValue(false)

         if(accessToken.isEmpty()){

             accessDeniedLiveData.postValue("")
             println("Testing ")
         }else {

             println("Refresh Token $accessToken")
             println("Sku $sku")
             println("Purchase Token  $purchaseToken")
             println("Package Name $packageName")

             val subscriptionStatus = subscriptionApi.getSubscriptionDetails(
                 packageName,
                 sku,
                 purchaseToken,
                 accessToken
             )
             println("Original Exprie Time " + DateUtils.getDateTime(subscriptionStatus.expiryTimeMillis.toLong()))
             localDataSource.updateSubscriptions(subscriptionStatus)
         }
     }












}