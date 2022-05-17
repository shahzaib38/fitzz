package imagetrack.app.lanuguages

import imagetrack.app.utils.Model

data class LanguageSupportModel constructor( var dataId :Int, var name: String, var languageKey: String, var imageSupport: String) : BaseLanguageModel(dataId){


    override fun toString(): String {
        return name
    }
}