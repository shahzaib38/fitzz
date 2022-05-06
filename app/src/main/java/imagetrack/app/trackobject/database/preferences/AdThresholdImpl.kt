package imagetrack.app.trackobject.database.preferences

import android.content.Context


private const val CLICKED_PREFERENCES_NAME = "threshold_preferences"
private const val CLICKED_DATA = "threshold_data"
private val DEFAULT :Int = 0
private const val REPEAT_DATA  ="repeat_data"

class AdThresholdImpl(private val context : Context) : AdThreshold {


    private val sharedPreferences =
        context.applicationContext.getSharedPreferences(CLICKED_PREFERENCES_NAME, Context.MODE_PRIVATE)
         val editor =   sharedPreferences.edit()

    override suspend fun save(clicks : Int) {
        val totalClicks =clicks + getMaxClicks()

        println("total clicks "+totalClicks)
        editor.putInt(CLICKED_DATA ,totalClicks)
        editor.apply() }

    override fun getMaxClicks(): Int {
        return sharedPreferences.getInt(CLICKED_DATA,DEFAULT)
    }

    override fun isMaxClickedPerformed(): Boolean {

        return getMaxClicks() >=3
    }



    override fun getRepeat(): Int {
        return sharedPreferences.getInt(REPEAT_DATA,DEFAULT)
    }





    override suspend fun setRepeat(plus: Int) {

        val totalRepeat =plus + getRepeat()
        println("total Repeat "+totalRepeat)

        editor.putInt(REPEAT_DATA ,totalRepeat)
        editor.apply() }

    override fun canReset(): Boolean {
        return getRepeat() >3 }

    override fun setRepeatZero() {
        editor.putInt(REPEAT_DATA ,0)
        editor.apply() }

    override suspend fun setThresholdZero() {
        editor.putInt(CLICKED_DATA ,0)
        editor.apply()
    }


}