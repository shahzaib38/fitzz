package imagetrack.app.lanuguages

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("translations")
    @Expose
    private var translations: List<Translation?>? = null

    fun getTranslations(): List<Translation?>? {
        return translations
    }

    fun setTranslations(translations: List<Translation?>?) {
        this.translations = translations
    }

}