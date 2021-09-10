package imagetrack.app.trackobject.database.local.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import imagetrack.app.utils.Model

@Entity
data class HistoryBean(   @PrimaryKey(autoGenerate = true) var historyId :Int  = 0,
    @ColumnInfo(name = "historyvalue") var value: String? = null , var date :String?=""
    , @Ignore var isselected :Boolean=false
) : Model(historyId){


}