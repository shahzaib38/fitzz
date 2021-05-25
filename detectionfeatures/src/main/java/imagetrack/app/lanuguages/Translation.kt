package imagetrack.app.lanuguages

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Translation {

    @SerializedName("translatedText")
    @Expose
    private var translatedText: String? = null
    @SerializedName("detectedSourceLanguage")
    @Expose
    private var detectedSourceLanguage: String? = null

    fun getTranslatedText(): String? {
        return translatedText
    }

    fun setTranslatedText(translatedText: String?) {
        this.translatedText = translatedText
    }

    fun getDetectedSourceLanguage(): String? {
        return detectedSourceLanguage
    }

    fun setDetectedSourceLanguage(detectedSourceLanguage: String?) {
        this.detectedSourceLanguage = detectedSourceLanguage
    }}