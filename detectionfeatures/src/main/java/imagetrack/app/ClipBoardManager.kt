package imagetrack.app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipBoardManager private constructor(private val clipBoardManager: ClipboardManager) {



    fun copy(text  :String ){

        val clip: ClipData = ClipData.newPlainText(TEXT_KEY, text)
        clipBoardManager.setPrimaryClip(clip)

    }




    companion object {

        private const val TEXT_KEY ="ScanDocument"



        fun clipInstance(context: Context): ClipBoardManager {
            var clipBoardManager: ClipBoardManager? = null

            if (clipBoardManager == null) {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                return ClipBoardManager(clipboard)
            }

            return clipBoardManager
        }

    }


}