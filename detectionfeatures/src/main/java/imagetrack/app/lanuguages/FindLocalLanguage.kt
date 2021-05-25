package imagetrack.app.lanuguages

object FindLocalLanguage {


    fun internationalLocalLanguage(language: String?): String? {
        val speech = "Speech is not available"
        return when (language) {
            "af" -> "af-ZA"
            "am" -> "am-ET"
            "hy" -> "hy-AM"
            "az" -> "az-AZ"
            "bn" -> "bn-BD"
            "ms" -> "ms-MY"
            "ca" -> "ca-ES"
            "cs" -> "cs-CZ"
            "da" -> "da-DK"
            "de" -> "de-DE"
            "es" -> "es-AR"
            "gl" -> "gl-ES"
            "ka" -> "ka-GE"
            "gu" -> "gu-IN"
            "hr" -> "hr-HR"
            "zu" -> "zu-ZA"
            "is" -> "is-IS"
            "jv" -> "jv-ID"
            "kn" -> "kn-IN"
            "km" -> "km-KH"
            "lo" -> "lo-LA"
            "lv" -> "lv-LV"
            "lt" -> "lt-LT"
            "hu" -> "hu-HU"
            "ml" -> "ml-IN"
            "mr" -> "mr-IN"
            "nl" -> "nl-NL"
            "ne" -> "ne-NP"
            "nb" -> "nb-NO"
            "pl" -> "pl-PL"
            "pt" -> "pt-BR"
            "ro" -> "ro-RO"
            "si" -> "si-LK"
            "sk" -> "sk-SK"
            "sl" -> "sl-SI"
            "su" -> "su-ID"
            "sw" -> "sw-TZ"
            "fi" -> "fi-FI"
            "sv" -> "sv-SE"
            "ta" -> "ta-IN"
            "te" -> "te-IN"
            "vi" -> "vi-VN"
            "tr" -> "tr-TR"
            "ur" -> "ur-PK"
            "el" -> "el-GR"
            "bg" -> "bg-BG"
            "ru" -> "ru-RU"
            "sr" -> "sr-RS"
            "uk" -> "uk-UA"
            "he" -> "he-IL"
            "hi" -> "hi-IN"
            "ar", "bs", "mk" -> "ar-XA"
            "en" -> "en-GB"
            "fil" -> "fil-PH"
            "et" -> "fi-FI"
            "fr" -> "fr-CA"
            "id" -> "id-ID"
            "it", "sq", "eo", "la" -> "it-IT"
            "ja" -> "ja-JP"
            "ko" -> "ko-KR"
            "zh-CN", "zh-TW" -> "cmn-CN"
            "no" -> "pl-PL"

            else -> speech
        }
    }


}