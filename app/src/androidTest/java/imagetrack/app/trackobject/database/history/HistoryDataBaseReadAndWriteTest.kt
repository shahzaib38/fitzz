package imagetrack.app.trackobject.database.history

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.database.local.history.HistoryDao
import imagetrack.app.trackobject.database.local.history.HistoryDatabase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
class HistoryDataBaseReadAndWriteTest {

  private  lateinit var historyDao : HistoryDao
 private lateinit var database : HistoryDatabase


    @Before
    fun createDb(){
     val context =   ApplicationProvider.getApplicationContext<Context>()
      database = Room.inMemoryDatabaseBuilder(context  ,HistoryDatabase::class.java).allowMainThreadQueries().build()
        historyDao  = database.userDao()


    }

    @Test
    fun insertData(){
//      val testHistory =  HistoryBean(1, "Supply", "DataBase", true)
//        runBlocking {
//            historyDao.insert(testHistory)
//
//          val historyBean1 =  historyDao.getAll()[0]
//
//            Assert.assertEquals(historyBean1,testHistory)
//
//        }



    }





    @After
    fun closeDataBase(){
        database.close()
    }





}