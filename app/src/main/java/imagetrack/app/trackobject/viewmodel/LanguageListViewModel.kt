package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import imagetrack.app.lanuguages.TranslateDetect
import imagetrack.app.trackobject.navigator.LanguageListNavigator
import imagetrack.app.trackobject.repo.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback

class LanguageListViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : BaseViewModel<LanguageListNavigator>(mainRepository) {



    val subscriptionLiveData = mainRepository.subscriptionLiveData


    var mutableLiveData = MutableLiveData<String>()
    val translate :LiveData<String> = mutableLiveData


    fun  close(){
        getNavigator().close()

    }



    fun getUsersFlow(name: MutableMap<String, String>){

        viewModelScope.launch(Dispatchers.IO) {


        val data =    mainRepository.getUsers(name).getData()

            if(data!=null){
             val translation =   data.getTranslations()

                if(translation!=null){

                    for(translate in translation){

                        if(translate!=null) {
                            var translatedText = translate.getTranslatedText()
                            if(translatedText!=null && translatedText.isNotEmpty()){


                                mutableLiveData.postValue(translatedText)

                            }

                        }
                    }

                }

            }




        }

    }

    fun getUsers(name: MutableMap<String, String>) = liveData(Dispatchers.IO) {
        try {
            mainRepository.getUsers(name).getData()?.getTranslations()?.forEach {
             emit(it?.getTranslatedText()) }

        } catch (exception: Exception) {
            emit( exception.message ?: "Error Occurred!")
       // emit( "Error Occurred!")

         }


    }



}