package imagetrack.app.di

import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun getString():String {

        return "shahzaib"

    }

    @Singleton
    @Provides
    fun getRecognizer():TextRecognizer{

        return  TextRecognition.getClient()
    }



}
