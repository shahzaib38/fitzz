package imagetrack.app.trackobject.database.preferences

import android.content.Context


interface AdThreshold {

    suspend fun save(clicks : Int )
    fun getMaxClicks():Int
    fun isMaxClickedPerformed():Boolean
     fun getRepeat() :Int
     suspend  fun setRepeat(plus : Int )
    fun canReset():Boolean
    fun setRepeatZero()
    suspend fun setThresholdZero()



    companion object {

        @Volatile
        private var INSTANCE: AdThreshold? = null

        fun getInstance(context : Context): AdThreshold =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AdThresholdImpl(context).also { INSTANCE = it }
            }

    }


}