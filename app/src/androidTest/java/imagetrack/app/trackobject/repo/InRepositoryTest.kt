package imagetrack.app.trackobject.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import imagetrack.app.trackobject.database.local.SubscriptionStatusDao
import imagetrack.app.trackobject.database.local.inappdatabase.AppDatabase
import org.junit.Before
import org.junit.Rule
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject


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