package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import imagetrack.app.trackobject.navigator.SettingsNavigator
import imagetrack.app.trackobject.repo.SettingsRepository

class SettingsViewModel  @ViewModelInject constructor(private val settingRepository : SettingsRepository) : BaseViewModel<SettingsNavigator>(settingRepository)  {

    val subscriptionLiveData = settingRepository.subscriptionLiveData


}