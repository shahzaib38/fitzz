package imagetrack.app.pdf

import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PDFUtil private constructor(){


    companion object{

        private val TAG = PDFUtil::class.java.name

        /**
         * Page width for our PDF.
         */
        const val PDF_PAGE_WIDTH = 8.3 * 40    //332

        /**
         * Page height for our PDF.
         */
        const val PDF_PAGE_HEIGHT = 11.7 * 72  //842.4

        private var   sInstance : PDFUtil?=null ;

        public fun   getInstance(): PDFUtil? {
            if (sInstance == null) {
                sInstance =  PDFUtil();
            }
            return sInstance;
        }


    }

    fun generatePDF(
        text: String,
        filePath: String,
        listener: PDFUtilListener
    ) {
        //Check Api Version.
        val currentApiVersion = Build.VERSION.SDK_INT
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            // Kitkat
            println("Generate Pf")
            GeneratePDFAsync(text, filePath, listener).execute(text)
        } else {
            // Before Kitkat
            Log.e(TAG, "Generate PDF is not available for your android version.")
            listener.pdfGenerationFailure(
                Exception("Generate PDF is not available for your android version.")
            )
        }
    }

    interface PDFUtilListener {
        /**
         * Called on the success of PDF Generation.
         */
        fun pdfGenerationSuccess()

        /**
         * Called when PDF Generation failed.
         *
         * @param exception Exception occurred during PDFGeneration.
         */
        fun pdfGenerationFailure(exception: Exception?)
    }

     class GeneratePDFAsync(
         private val text: String,
         private val filePath: String,
         private val mListener: PDFUtilListener
     ): AsyncTask<String, Void, Boolean>(){

         var i :Int =0
         override fun doInBackground(vararg params: String): Boolean {

             val isPdfSuccessfullyGenerated: Boolean
             isPdfSuccessfullyGenerated = try {
                 // Create PDF Document.
                 val pdfDocument = PdfDocument()

                 println("Do in Background")
                 // Write content to PDFDocument.
                 writePDFDocument(params[0], pdfDocument)

                 // Save document to file.
                 savePDFDocumentToStorage(pdfDocument)
                 true

             } catch (exception: java.lang.Exception) {
                 Log.e(TAG, exception.message)
                 false
             }
             return isPdfSuccessfullyGenerated
         }

         override fun onPostExecute(isPdfSuccessfullyGenerated: Boolean) {
             super.onPostExecute(isPdfSuccessfullyGenerated)
             if (isPdfSuccessfullyGenerated) {
                 //Send Success callback.
                 mListener.pdfGenerationSuccess()
             } else {
                 //Send Error callback.
                 mListener.pdfGenerationFailure(Exception("Pdf Not Generated"))
             }
         }
         private fun writePDFDocument(data: String, pdfDocument: PdfDocument) {
//
          val character =   data.toCharArray()
//             val numOfLoops = character.size / PDF_PAGE_WIDTH

//             println("Charachter sizees" +numOfLoops)

             val pageInfo = PageInfo.Builder(PDF_PAGE_WIDTH.toInt(), PDF_PAGE_HEIGHT.toInt(), 1).create()

             // start a page
             val page = pdfDocument.startPage(pageInfo)

             // draw view on the page
             val pageCanvas = page.canvas
             val paint = Paint()


             var verticalLine =0
             var XAxis =10F
             var YAXIS =10F
              var numOf = 0
              var temoindex =0
             var tempcount =58
             while(verticalLine<8) {

                     pageCanvas.drawText(character, temoindex,tempcount ,10F ,YAXIS,paint)

                 temoindex =tempcount +1
                 tempcount *= 2
if(tempcount>=character.size){
    break

}
                 verticalLine++
                  YAXIS +=20


             }


             // finish the page
             pdfDocument.finishPage(page)



         }





         @Throws(IOException::class)
         private fun savePDFDocumentToStorage(pdfDocument: PdfDocument) {
             var fos: FileOutputStream? = null
             // Create file.

             println("Saving to File")
            // val pdfFile: File = File("pdf", "maooo")
             val pdfFile =File(
                 Environment.getExternalStorageDirectory()
                     .toString() + "/" + File.separator + "$filePath.pdf"
             )
             //Create parent directories
             val parentFile = pdfFile.parentFile
             if(parentFile!=null) {
                 check(!(!parentFile.exists() && !parentFile.mkdirs())) { "Couldn't create directory: $parentFile" }
             }

             var fileExists = pdfFile.exists()
             // If File already Exists. delete it.
             if (fileExists) {
                 fileExists = !pdfFile.delete()
             }
             try {
                 if (!fileExists) {
                     // Create New File.
                     fileExists = pdfFile.createNewFile()
                 }
                 if (fileExists) {
                     // Write PDFDocument to the file.
                     fos = FileOutputStream(pdfFile)
                     pdfDocument.writeTo(fos)

                     //Close output stream
                     fos.close()

                     // close the document
                     pdfDocument.close()
                 }
             } catch (exception: IOException) {
                 println("Exception $exception")
                 fos?.close()
                 throw exception
             }
         }

     }


}