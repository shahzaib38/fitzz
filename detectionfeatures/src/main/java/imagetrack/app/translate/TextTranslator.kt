//package imagetrack.app.translate
//
//import androidx.lifecycle.LiveData
//import imagetrack.app.error.KeyNotFoundException
//import imagetrack.app.lanuguages.LanguageArray
//import imagetrack.app.lanuguages.TranslateDetect
//import javax.inject.Inject
//
//class TextTranslator @Inject constructor( val translateApi : TranslateApi)  : ITranslator {
//
//    override fun  getTargetKey(target :String):String{
//      val languageArray =  LanguageArray.arrayValues()
//        for(languageModel in languageArray){
//
//            if(target.equals(languageModel.name)){
//
//                return languageModel.languageKey
//            }
//        }
//
//        throw KeyNotFoundException("Key Not Found ") }
//
//
//
//    override fun autoDetect(resultText :String ,target :String )  :LiveData<TranslateDetect>{
//
//        val postParameters: MutableMap<String, String> = HashMap()
//        postParameters["q"] = resultText as String
//        postParameters["target"] =target
//        postParameters["key"] = TranslateUtils.SCANNER_KEY
//
//       return  translateApi.getData(postParameters)
//
//    }
//
//    override fun manualDetect(resultText: String, target: String): LiveData<TranslateDetect> {
//        val postParameters: MutableMap<String, String> = HashMap()
//        postParameters["q"] = resultText as String
//        postParameters["target"] =target
//        postParameters["key"] = TranslateUtils.SCANNER_KEY
//
//        return  translateApi.getData(postParameters)
//
//    }
//
//
//}