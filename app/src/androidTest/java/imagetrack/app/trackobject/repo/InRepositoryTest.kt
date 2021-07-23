package imagetrack.app.trackobject.repo

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.android.billingclient.api.Purchase
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.database.local.ILocalDataSource
import imagetrack.app.trackobject.database.local.LocalDataSource
import imagetrack.app.trackobject.database.local.SubscriptionStatusDao
import imagetrack.app.trackobject.database.local.inappdatabase.AppDatabase
import imagetrack.app.trackobject.database.local.inappdatabase.SubscriptionJson
import imagetrack.app.trackobject.getOrAwaitValue
import imagetrack.app.trackobject.inapppurchaseUtils.Constants
import imagetrack.app.trackobject.inapppurchaseUtils.purchaseForSku
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
class InRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    lateinit var inAppRepository: InAppRepository

    @Inject
    lateinit var appDatabase: AppDatabase

    lateinit var subscriptionDao : SubscriptionStatusDao

    @Before
    fun setUp() {
        hiltRule.inject()

     val database =   Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),AppDatabase::class.java,).allowMainThreadQueries().build()

        subscriptionDao = database.subscriptionStatusDao()

    }


    private fun readTestData(inputStream: InputStream): String {
      //  val inputStream = javaClass.getResourceAsStream(filename)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        do {
            line = reader.readLine()
            stringBuilder.append(line ?: "")
        } while (line != null)
        return stringBuilder.toString()
    }


}