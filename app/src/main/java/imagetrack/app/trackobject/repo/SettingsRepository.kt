package imagetrack.app.trackobject.repo

import imagetrack.app.trackobject.database.local.ILocalDataSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingsRepository @Inject constructor( private val localDataSource: ILocalDataSource): BaseRepository() {

    val subscriptionLiveData = localDataSource.getSubscriptionJsonLiveData()



}