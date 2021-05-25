package imagetrack.app.lanuguages

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TranslatePost  {

    @SerializedName("q")
    @Expose
    private var q: String? = null
    @SerializedName("target")
    @Expose
    private var target: String? = null

    fun TranslatePost(q: String?, target: String?) {
        this.q = q
        this.target = target }

    fun getQ(): String? {
        return q }


    fun getTarget(): String? {
        return target }


}