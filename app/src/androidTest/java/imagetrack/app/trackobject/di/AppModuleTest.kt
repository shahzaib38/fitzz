package imagetrack.app.trackobject.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import imagetrack.app.trackobject.database.local.LocalDataSource
import imagetrack.app.trackobject.database.local.inappdatabase.AppDatabase
import imagetrack.app.trackobject.inapppurchaseUtils.AppExecutors
import imagetrack.app.trackobject.inapppurchaseUtils.BillingClientLifecycle
import imagetrack.app.trackobject.repo.InAppRepository
import imagetrack.app.trackobject.token_loader.TokenLoader
import javax.inject.Named


@InstallIn(ApplicationComponent::class)
@Module
object AppModuleTest {


    @Named
    @Provides
    fun getInAppRepository(localDataSource: LocalDataSource ,
                           billingClientLifecycle: BillingClientLifecycle ,
                           tokenLoader : TokenLoader): InAppRepository{
        return InAppRepository(localDataSource ,billingClientLifecycle,tokenLoader) }


    @Named
    @Provides
    fun billingClientLifecyclle(application: Application) : BillingClientLifecycle {
        return BillingClientLifecycle.getInstance(application) }



    @Named
    @Provides
    fun getLocalDataSource(appDatabase: AppDatabase ,executors: AppExecutors) : LocalDataSource{
        return LocalDataSource.getInstance(executors,appDatabase ) }





    @Named
    @Provides
    fun provideTransferRetrofit(application: Application) : AppDatabase {
        return Room.inMemoryDatabaseBuilder(application,AppDatabase::class.java,).allowMainThreadQueries().build()
    }


    @Named
    @Provides
    fun provideExecutors() : AppExecutors {
        return AppExecutors() }


}