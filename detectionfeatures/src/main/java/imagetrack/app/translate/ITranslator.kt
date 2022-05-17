package imagetrack.app.translate

import androidx.lifecycle.LiveData
import imagetrack.app.lanuguages.TranslateDetect

interface ITranslator {

    fun  getTargetKey(target :String):String
    fun autoDetect(resultText :String ,target :String )  : LiveData<TranslateDetect>
    fun manualDetect(resultText :String ,target :String )  : LiveData<TranslateDetect>



}