package imagetrack.app.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object DateUtils {


    fun getTime():Date{

        return  Calendar.getInstance().time
    }


    fun getDateTime(dateInMillis: Long): String? {
        val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy, hh:mm a", Locale.getDefault())
        return dateFormat.format(Date(dateInMillis)) }

}