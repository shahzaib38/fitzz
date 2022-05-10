package imagetrack.app.trackobject.ext

import  android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import imagetrack.app.trackobject.helpers.*
import imagetrack.app.trackobject.inapppurchaseUtils.SubscriptionNote
import imagetrack.app.trackobject.ui.dialogs.*
import java.io.File


fun Activity.tryOpenPathIntent(path: String, openAsType: Int = OPEN_AS_DEFAULT) {

        val uri = if (isNougatPlus()) {
            FileProvider.getUriForFile(this, "${APPLICATION_ID}.provider", File(path))

        } else {
            Uri.fromFile(File(path))
        }

        Intent().apply {
            action = Intent.ACTION_VIEW
            setDataAndType(uri, getMimeType(openAsType))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(this)
        }

}



/** Scan Dialog Fragment **/
// fun AppCompatActivity.showScanDialog(it: String) {
//        ScanDialogFragment
//            .getInstance(it)
//            .showDialog(this.supportFragmentManager) }

/** Instruction Dialog Fragment **/
fun AppCompatActivity.showInstructionDialog(){
    InstructionDialog.getInstance().showDialog(this.supportFragmentManager) }

/** Activity Launcher **/
fun AppCompatActivity.launchActivity(className : Class<out Activity>){
    val intent =Intent(this , className)
    startActivity(intent) }

/** Subscription Status Dialog **/
fun AppCompatActivity.subscriptionStatusDialog(subscriptionNote: SubscriptionNote){
    SubscriptionStatusDialog.getInstance(subscriptionNote)
        .showDialog(this.supportFragmentManager)
}



/** Subscription Detail Dialog **/
fun AppCompatActivity.subscriptionDetailDialog(subscriptionNote: SubscriptionNote){
    SubscriptionDetailDialog.getInstance(subscriptionNote)
        .showDialog(this.supportFragmentManager) }

 fun AppCompatActivity.toast(name :String ){
    Toast.makeText(this ,name , Toast.LENGTH_LONG).show() }


/** PDF  Dialog Fragment **/
fun AppCompatActivity.showPdf(text :String){
    PdfCreatorDialog.getInstance(text).showDialog(this.supportFragmentManager) }


/** InternetConnection  Dialog Fragment **/
fun AppCompatActivity.internetConnectionDialog(){
    InternetConnectionDialog.getInstance().showDialog(this.supportFragmentManager) }


/** Language List  Dialog Fragment **/
//fun AppCompatActivity.showLanguageList(text :String ){
//    LanguageListDialogFragment.getInstance(text).showDialog(this.supportFragmentManager) }
//


/** Open Dialog Fragment **/
fun AppCompatActivity.showOpenDialog(){
    OpenPdfDialog.getInstance("").showDialog(this.supportFragmentManager) }



fun isNougatPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N


private fun getMimeType(type : Int) =when(type){

    OPEN_AS_DEFAULT-> ""
    OPEN_AS_TEXT -> "text/*"
    OPEN_AS_IMAGE -> "image/*"
    OPEN_AS_AUDIO -> "audio/*"
    OPEN_AS_VIDEO -> "video/*"
    OPEN_AS_PdF -> "application/pdf"
    else -> "*/*"


}
