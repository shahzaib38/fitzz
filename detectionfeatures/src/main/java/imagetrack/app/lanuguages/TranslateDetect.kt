package imagetrack.app.lanuguages

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TranslateDetect  {

    @SerializedName("data")
    @Expose
    private var data: Data? = null

    fun getData(): Data? {
        return data
    }

    fun setData(data: Data?) {
        this.data = data
    }


}