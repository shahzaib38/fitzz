package imagetrack.app.trackobject.database.local.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import imagetrack.app.utils.Model

@Entity
class HistoryBean(   @PrimaryKey(autoGenerate = true) var id :Int  = 0,
    @ColumnInfo(name = "historyvalue") var value: String? = null , var date :String?=""
    , @Ignore var isselected :Boolean=false
) : Model(){


}