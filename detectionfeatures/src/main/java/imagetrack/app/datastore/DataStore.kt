package imagetrack.app.datastore

import android.content.Context


private const val WELCOME_PREFERENCES_NAME = "welcome_preferences"
private const val WELCOME_DATA = "welcome_data"
private val DEFAULT :Boolean =false

class DataStore(private val context : Context) :IDataStore {


    private val sharedPreferences =
        context.applicationContext.getSharedPreferences(WELCOME_PREFERENCES_NAME, Context.MODE_PRIVATE)
    val editor =   sharedPreferences.edit()

    override suspend fun save(welcomeNote: WelcomeNote) {
        editor.putBoolean(WELCOME_DATA ,welcomeNote.isNew)

       editor.apply()

    }

    override fun getWelcomeNote(): Boolean{


        return sharedPreferences.getBoolean(WELCOME_DATA,DEFAULT)


    }


}