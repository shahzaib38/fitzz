package imagetrack.app.trackobject.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import imagetrack.app.image_processing.CloudTextRecognition
import imagetrack.app.image_processing.DeviceTextRecognizer
import imagetrack.app.image_processing.VisionImageProcessor
import imagetrack.app.trackobject.database.local.ILocalDataSource
import imagetrack.app.trackobject.database.local.LocalDataSource
import imagetrack.app.trackobject.database.local.history.HistoryDatabase
import imagetrack.app.trackobject.database.local.inappdatabase.AppDatabase
import imagetrack.app.trackobject.inapppurchaseUtils.AppExecutors
import imagetrack.app.trackobject.inapppurchaseUtils.BillingClientLifecycle
import imagetrack.app.trackobject.token_loader.TokenLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object InAppModule {

    @Provides
    fun provideHistoryDatabase(application: Application): HistoryDatabase {

        return HistoryDatabase.getInstance(application) }

    @Provides
    fun provideTransferRetrofit(application: Application) : AppDatabase {
        return AppDatabase.getInstance(application) }


    @Provides
    @Singleton
    fun provideVisionImageProcessor(@ApplicationContext context : Context) :
      VisionImageProcessor = DeviceTextRecognizer(context)



            @Provides
    fun provideExecutors() : AppExecutors{
        return AppExecutors() }


    @Singleton
    @Provides
    fun getTokenLoader(@ApplicationContext context: Context) : TokenLoader{
        return TokenLoader(context) }


    @Provides
    fun localDataSource(executors: AppExecutors , database : AppDatabase) : ILocalDataSource
    { return LocalDataSource.getInstance(executors, database) }

//
    @Provides
    fun billingClientLifecyclle(application: Application) : BillingClientLifecycle {
        return BillingClientLifecycle.getInstance(application) }



}

