package imagetrack.app.trackobject.repo

import com.android.billingclient.api.Purchase
import com.google.auth.oauth2.AccessToken
import imagetrack.app.trackobject.database.local.LocalDataSource
import imagetrack.app.trackobject.database.local.inappdatabase.SubscriptionApi
import imagetrack.app.trackobject.database.local.inappdatabase.SubscriptionJson
import imagetrack.app.trackobject.inapppurchaseUtils.BillingClientLifecycle
import imagetrack.app.trackobject.inapppurchaseUtils.Constants
import imagetrack.app.trackobject.inapppurchaseUtils.purchaseForSku
import imagetrack.app.trackobject.token_loader.TokenLoader
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class TestInAppRepository {


    @Test
    fun TestPurchaseProcessInit() =runBlocking{

        //given

        val billingClientLifecycle  =  mock(BillingClientLifecycle::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val purchase =mock(Purchase::class.java)
        val accessToken = mock(TokenLoader::class.java)
        val inAppRepository = InAppRepository(localDataSource,billingClientLifecycle,accessToken)

        Mockito.`when`(purchase.skus).thenReturn(arrayListOf(Constants.BASIC_SKU))

        //when
       inAppRepository.purchaseInit(arrayListOf(purchase))





    }




    @Test
    fun testCheckDataBaseHasNoPreviousPurchaseJson() = runBlocking{

        //given
        val billingClientLifecycle  =  mock(BillingClientLifecycle::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val purchase =mock(Purchase::class.java)
        val accessToken = mock(TokenLoader::class.java)
        val inAppRepository = InAppRepository(localDataSource,billingClientLifecycle,accessToken)

        Mockito.`when`(localDataSource.getPurchaseJson()).thenReturn(null)

        //when Act
        inAppRepository.processPurchaseForSubscriptionJson(purchase)

        //then Assert
        Mockito.verify(localDataSource ,times(2)).updatePurchaseSubscription("")



    }



    @Test
    fun testCheckDataBaseHasPreviousPurchaseJson() = runBlocking{

        //given
        val purchaseToken = "dkeeocmcjohaepgjkefaldpm.AO-J1Ow2DBNA2S6MViatBR5BVOHhoZjQsYsm57OwINdqIVIJ1zPWq5Lg2BeUQEZoN6ZyunNKiimpXBkIH5LXtgDzkbKYAgce6xdtqvC4MYQf1Ruu5L8vVkc"

        val billingClientLifecycle  =  mock(BillingClientLifecycle::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val purchase =mock(Purchase::class.java)
        val accessToken = mock(TokenLoader::class.java)
        val inAppRepository = InAppRepository(localDataSource,billingClientLifecycle ,accessToken)

        Mockito.`when`(purchase.isAcknowledged).thenReturn(false)
        Mockito.`when`(purchase.purchaseToken).thenReturn(purchaseToken)
        Mockito.`when`(localDataSource.getPurchaseJson()).thenReturn(SubscriptionJson("purchase",""))

        //when Act
        inAppRepository.processPurchaseForSubscriptionJson(purchase)

        //then Assert
        Mockito.verify(localDataSource , never()).updatePurchaseSubscription("")



    }

    @Test
    fun testRegisterPurchaseToLocalDataBase() = runBlocking{
        val packageName ="imagetrack.app.trackobject"
        val purchaseToken = "dkeeocmcjohaepgjkefaldpm.AO-J1Ow2DBNA2S6MViatBR5BVOHhoZjQsYsm57OwINdqIVIJ1zPWq5Lg2BeUQEZoN6ZyunNKiimpXBkIH5LXtgDzkbKYAgce6xdtqvC4MYQf1Ruu5L8vVkc"
        //val purchase =mock(Purchase::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val purchase =mock(Purchase::class.java)
        val accessToken = mock(TokenLoader::class.java)
        val billingClientLifecycle  =  mock(BillingClientLifecycle::class.java)

        val inAppRepository = InAppRepository(localDataSource,billingClientLifecycle ,accessToken)

         val subscriptionApi =mock(SubscriptionApi::class.java)

        Mockito.`when`(purchase.orderId).thenReturn(Constants.BASIC_SKU)
        Mockito.`when`(purchase.purchaseToken).thenReturn(purchaseToken)
        Mockito.`when`(purchase.packageName).thenReturn(packageName)


        inAppRepository.processPurchaseForSubscriptionJson(purchase)

    }





}