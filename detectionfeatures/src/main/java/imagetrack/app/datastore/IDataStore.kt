package imagetrack.app.datastore

import android.content.Context

interface IDataStore {

   suspend fun save(welcomeNote: WelcomeNote)

    fun getWelcomeNote():Boolean

    companion object {

        @Volatile
        private var INSTANCE: IDataStore? = null

        fun getInstance(context : Context): IDataStore =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataStore(context).also { INSTANCE = it }
            }

    }


}