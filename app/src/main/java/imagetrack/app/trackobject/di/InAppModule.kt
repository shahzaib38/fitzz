package imagetrack.app.trackobject.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import imagetrack.app.trackobject.database.local.history.HistoryDatabase

import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object InAppModule {

    @Provides
    fun provideHistoryDatabase(application: Application): HistoryDatabase {
        return HistoryDatabase.getInstance(application) }


}