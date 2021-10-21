package imagetrack.app.trackobject.common

import imagetrack.app.trackobject.model.SettingBean

object SettingsUtils {


    fun arrayList() = arrayListOf(SettingBean(HISTORY) ,SettingBean(VIEW_PDF) ,
        SettingBean(PURCHASE_REPORT),SettingBean(SHARE_APP)


    )


    const val EMAIL: String="crystalbusinessapps@gmail.com"
    const val HISTORY = "History"
    const val VIEW_PDF= "All Pdf Files"
    const val PURCHASE_REPORT = "Purchase Report"
     const val SHARE_APP ="Share App"

    const val CONTACT_DEVELOPER ="Contact us"


}