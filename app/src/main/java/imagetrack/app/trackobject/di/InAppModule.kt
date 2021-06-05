package imagetrack.app.trackobject.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import imagetrack.app.trackobject.database.local.history.AppDatabase
import imagetrack.app.trackobject.database.local.LocalDataSource
import imagetrack.app.trackobject.database.local.history.HistoryDatabase
import imagetrack.app.trackobject.database.network.firebase.AppExecutors
import imagetrack.app.trackobject.database.network.firebase.ServerFunctions
import imagetrack.app.trackobject.database.network.firebase.WebDataSource
import imagetrack.app.trackobject.in_app_purchase.BillingClientLifecycle
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object InAppModule {



    @Singleton
    @Provides
    fun getBillingClientLifecycle(application :Application):BillingClientLifecycle {
       return  BillingClientLifecycle.getInstance(application)
    }


    @Provides
    fun  getWebDataSource(executors :AppExecutors ,serverFunctions : ServerFunctions) : WebDataSource{
        return  WebDataSource.getInstance(executors, serverFunctions) }


    @Provides
    fun getServerFunction() : ServerFunctions {
        return  ServerFunctions.getInstance() }

    @Provides
    fun getAppDataBase(application: Application) : AppDatabase {
        return AppDatabase.getInstance(application) }



    @Singleton
    @Provides
    fun getExecutors() : AppExecutors{
      return   AppExecutors() }


    @Provides
    fun getLocalDataSource(executors :AppExecutors  ,database : AppDatabase) : LocalDataSource{
        return LocalDataSource.getInstance(executors, database) }


    @Provides
    fun provideHistoryDatabase(application: Application): HistoryDatabase {
        return HistoryDatabase.getInstance(application) }


}