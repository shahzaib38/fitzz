package imagetrack.app.trackobject.common

import imagetrack.app.trackobject.model.SettingBean

object SettingsUtils {


    fun arrayList() = arrayListOf(SettingBean(0,HISTORY) ,SettingBean(1,VIEW_PDF) ,
        SettingBean(2,PURCHASE_REPORT),SettingBean(3,SHARE_APP),
        SettingBean(4,ENABLE_COPY_TO_TRANSLATE)
    )


    const val EMAIL: String="crystalbusinessapps@gmail.com"
    const val HISTORY = "History"
    const val VIEW_PDF= "All Pdf Files"
    const val PURCHASE_REPORT = "Purchase Report"
     const val SHARE_APP ="Share App"

    const val CONTACT_DEVELOPER ="Contact us"
    const val ENABLE_COPY_TO_TRANSLATE ="enable copy to translate"



}