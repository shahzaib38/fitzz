package imagetrack.app.trackobject.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import imagetrack.app.trackobject.database.local.history.HistoryDatabase


@Module
@InstallIn(ApplicationComponent::class)
object InAppModule {

    @Provides
    fun provideHistoryDatabase(application: Application): HistoryDatabase {
        return HistoryDatabase.getInstance(application) }

//    @Provides
//    fun provideTransferRetrofit(application: Application) :AppDatabase{
//        return AppDatabase.getInstance(application) }
//
//
//    @Provides
//    fun provideExecutors() : AppExecutors{
//        return AppExecutors() }
//
//    @Provides
//    fun provideServerFunctions()  :ServerFunctions{
//        return ServerFunctions.getInstance()}
//
//    @Provides
//    fun provideWebServer(executors : AppExecutors, serverFunctions : ServerFunctions) : WebDataSource{
//        return WebDataSource.getInstance(executors, serverFunctions) }
//
//
//    @Provides
//    fun localDataSource(executors: AppExecutors , database : AppDatabase) : LocalDataSource
//    { return LocalDataSource.getInstance(executors, database) }
//
//
//    @Provides
//    fun billingClientLifecyclle(application: Application) : BillingClientLifecycle{
//        return BillingClientLifecycle.getInstance(application) }


}

