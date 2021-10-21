package imagetrack.app.trackobject.token_loader

import android.content.Context
import com.google.auth.oauth2.GoogleCredentials
import dagger.hilt.android.qualifiers.ApplicationContext
import imagetrack.app.trackobject.camera_features.R
import java.io.IOException
import java.io.InputStream

class TokenLoader(@ApplicationContext private val context: Context) {




    fun getAccessToken() : String {
        val stream: InputStream = context.resources.openRawResource(R.raw.credential)

        return try {
            val credentials = GoogleCredentials.fromStream(stream)
                .createScoped(mutableListOf("https://www.googleapis.com/auth/androidpublisher"))
           credentials.refreshAccessToken().tokenValue
        }catch (ioException : IOException) {
            "" }
    }




}