package imagetrack.app.trackobject.viewmodel


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.navigator.ScanDialogNavigator
import imagetrack.app.trackobject.repo.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScanDialogViewModel @ViewModelInject constructor(private val mainRepository: MainRepository)  : BaseViewModel<ScanDialogNavigator>(mainRepository) {




    val subscriptionLiveData = mainRepository.subscriptionLiveData






    fun pdf(){

        getNavigator().pdf()
    }

    fun  saveNext(){

        getNavigator().exit()

    }

    fun edit(){
        getNavigator().edit()

    }

    fun copy(){
        getNavigator().copy()

    }

    fun share(){
        getNavigator().share()
    }


    fun translate(){
        getNavigator().translate()
    }

    fun insertHistory(historyBean : HistoryBean){

        viewModelScope.launch(Dispatchers.IO) {
            val isInserted  =    mainRepository.insertHistory(historyBean)

        }
    }




}