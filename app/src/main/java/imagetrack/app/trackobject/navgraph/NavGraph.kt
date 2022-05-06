package imagetrack.app.trackobject.navgraph

import androidx.navigation.NavController
import imagetrack.app.trackobject.ui.dialogs.LanguageListDialogFragmentDirections
import imagetrack.app.trackobject.ui.dialogs.ScanDialogFragmentDirections
import imagetrack.app.trackobject.ui.fragment.ScanFragmentDirections

object NavGraph {

   const  val SCAN_FRAGMENT_TO_PROGRESS =0
    const  val SCAN_FRAGMENT_TO_SCAN_DIALOG =1
    const val SCAN_DIALOG_TO_LIST_DIALOG =2
    const val LIST_DIALOG_TO_SCAN_DIALOG=3
    const val LIST_DiALOG_TO_INAPP =4
    const val GLOBAL_INTERNET_CONNECTION=5
    const val SCAN_TO_SPEAK =6
    const val SCAN_TO_SETTINGS =7

    fun navigate(id :Int , navController : NavController ,value :String =""){
      val direction =  when(id){
            SCAN_FRAGMENT_TO_PROGRESS ->{
                     ScanFragmentDirections.actionScanFragmentToProgressDialogFragment() }
          SCAN_FRAGMENT_TO_SCAN_DIALOG ->{
              ScanFragmentDirections.actionScanFragmentToScanDialogFragment(value) }

          SCAN_DIALOG_TO_LIST_DIALOG ->{
              ScanDialogFragmentDirections.actionScanDialogFragmentToLanguageListDialogFragment2(value) }

          LIST_DIALOG_TO_SCAN_DIALOG ->{
              LanguageListDialogFragmentDirections.actionLanguageListDialogFragmentToScanDialogFragment(value) }

          LIST_DiALOG_TO_INAPP ->{
              LanguageListDialogFragmentDirections.actionLanguageListDialogFragmentToInAppPurchaseActivity() }

          SCAN_TO_SPEAK ->{
              ScanDialogFragmentDirections.actionScanDialogFragmentToSpeakingDialogFragment(value) }
//
//          SCAN_TO_SETTINGS ->{
//              ScanFragmentDirections.actionScanFragmentToSettingsActivity() }

          else ->{ null }


        }



        if(direction !=null && navController!=null ) {
            navController.navigate(direction)
        }else throw NullPointerException("Direction or navController must not be null")




    }







}