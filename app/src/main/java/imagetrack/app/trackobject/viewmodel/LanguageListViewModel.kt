package imagetrack.app.trackobject.viewmodel

import android.util.Log
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
import kotlinx.coroutines.withContext
import javax.security.auth.callback.Callback

class LanguageListViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) : BaseViewModel<LanguageListNavigator>(mainRepository) {



    val subscriptionLiveData = mainRepository.subscriptionLiveData

    fun  close(){
        getNavigator().close()

    }


    var mutableLiveData = MutableLiveData<String>()
    val translate :LiveData<String> = mutableLiveData






    fun getUsersFlow(name: MutableMap<String, String> ,
                     startProgress: ()->Unit   ,
                     stopProgress :()->Unit ){

        startProgress()


        viewModelScope.launch(Dispatchers.IO) {


            val data =    mainRepository.getUsers(name).getData()

            if(data!=null){
                val translation =   data.getTranslations()

                if(translation!=null){

                    for(translate in translation){

                        if(translate!=null) {
                            var translatedText = translate.getTranslatedText()
                            if(translatedText!=null && translatedText.isNotEmpty()){




                            }

                        }
                    }

                }

            }


            withContext(Dispatchers.Main){
                stopProgress.invoke()

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