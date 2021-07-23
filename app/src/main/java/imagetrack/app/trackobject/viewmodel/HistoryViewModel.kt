package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import imagetrack.app.trackobject.database.local.history.HistoryBean
import imagetrack.app.trackobject.navigator.HistoryNavigator
import imagetrack.app.trackobject.repo.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel @ViewModelInject constructor(private val historyRepository : HistoryRepository)  : BaseViewModel<HistoryNavigator>(historyRepository) {



    fun getAllHistoryData() = liveData(Dispatchers.IO) {
        // emit(Resource.loading(data = null))


        try {
         val allData =  historyRepository.getAll()


                emit(allData)

            //}
        } catch (exception: Exception) {

        }


    }

    fun insertHistory(historyBean : HistoryBean){
        viewModelScope.launch(Dispatchers.IO) {
        val isInserted  =    historyRepository.insertHistory(historyBean)
          if(isInserted){
              getNavigator().notifyDataBase()
          }

        }
    }


    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
         val updated =   historyRepository.deleteAll()

            withContext(Dispatchers.Main) {
                if (updated) getNavigator().deleteAll()
            }

        }
    }


}