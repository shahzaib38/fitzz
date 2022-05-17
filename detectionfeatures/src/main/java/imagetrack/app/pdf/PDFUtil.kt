package imagetrack.app.pdf

import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.geom.Rectangle
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.property.BaseDirection
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.VerticalAlignment
import java.io.File
import java.io.FileNotFoundException
import java.util.regex.Pattern


class PDFUtil private constructor(private val context: Context){


    companion object{

        val directory =   File(Environment.getExternalStorageDirectory(), PDFUtil.PdfName)

        const val PdfName: String = "Sb Scanner"

        private val TAG = PDFUtil::class.java.name

        /**
         * average header Top Margin
         */
         const val averageTopMargin = 35.0f

        /**
         * SingleTon Instance PDFUTil
         */
        private var   INSTANCE  : PDFUtil?=null ;
        private val context : Context?=null

        fun   getInstance(context :Context): PDFUtil =
            INSTANCE?: synchronized(this){
                INSTANCE?: PDFUtil(context).also { INSTANCE =it } }
    }

    fun generatePDF(
        pdfMetaData: PDFMetaData,
        listener: PDFUtilListener
    ) {
        //Check Api Version.
        val currentApiVersion = Build.VERSION.SDK_INT
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            // Kitkat
            println("Generate Pf")
            GeneratePDFAsync(pdfMetaData.data, pdfMetaData.fileName, listener).execute(pdfMetaData)
        } else {
            // Before Kitkat
            Log.e(TAG, "Generate PDF is not available for your android version.")
            listener.pdfGenerationFailure(
                Exception("Generate PDF is not available for your android version.")
            )
        }



    }



     class GeneratePDFAsync(
         private val text: String,
         private val filePath: String,
         private val mListener: PDFUtilListener
     ): AsyncTask<PDFMetaData, Void, Boolean>(){

         override fun doInBackground(vararg params: PDFMetaData): Boolean {

             val isPdfSuccessfullyGenerated: Boolean

             isPdfSuccessfullyGenerated = try {
                 // Create PDF Document.


                 if(!directory.exists()){
                     directory.mkdir()

                 }

                 val pdfFile  =File(directory ,"$filePath.pdf")


                 val pdfDocument = PdfDocument(PdfWriter(pdfFile))

                 // Write content to PDFDocument.
                 writePDFDocument(params[0], pdfDocument)

                 true

             } catch (exception: FileNotFoundException) {
                 println("File ${exception.message}")

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

         private fun writePDFDocument(pdfMetaData : PDFMetaData, pdfDocument: PdfDocument) {

             val document = Document(pdfDocument)
             /**
              * Add Paragraph to Pdf File
              */
              createText(pdfMetaData.data, document)

             /**
              * Add HEader to Pdf File
              */
             if(pdfMetaData.header.isNotEmpty()) {
                 createHeader(pdfMetaData.header, document, pdfDocument)
             }

             // Close document
             /**
              * Close Doucment
              */
             document.close()

         }



       private   fun removeMultipleSpaces(text :String ):String{

           Log.i("Thread","${Thread.currentThread().name}")

           val stringBuffer =StringBuilder(text)
           val pattern =  Pattern.compile("\\s{2,}")

           val matcher =   pattern.matcher(stringBuffer.toString())

           while(matcher.find()){

               println("${matcher.start()}    "+"${matcher.end()}")

                stringBuffer.delete(matcher.start()+1,matcher.end())


           }
           return stringBuffer.toString() }






      private fun createText(text: String, document: Document) {
          val paragraphText=  text.trim()
          val noSpaceString =  removeMultipleSpaces(paragraphText)

          val noNewLine  =  noSpaceString.
              replace("\n", "")
             .replace("\r", "");

          val textParagraph = Paragraph(noNewLine)
              .setBaseDirection(BaseDirection.RIGHT_TO_LEFT)
              .setMultipliedLeading(1.5f).setMarginTop( averageTopMargin)

          document.add(textParagraph)
      }

         private fun createHeader(header: String, document: Document, pdfDoc: PdfDocument) {

             val fontSize =32f
             val topAlignment=60

            val headerParagraph =   Paragraph(header)
                .setFont(PdfFontFactory
                .createFont(StandardFonts.HELVETICA))
                 .setFontSize(fontSize).setMarginTop(averageTopMargin)

             println("Ddocument Size " + pdfDoc.numberOfPages)
             for(i in 1..pdfDoc.numberOfPages) {

                 val pageSize: Rectangle = pdfDoc.getPage(i).pageSize
                 val x: Float = pageSize.width / 2.0f
                 val y: Float = pageSize.top - topAlignment

                 document.showTextAligned(headerParagraph, x, y, i, TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0f)

             }

         }

     }






    /**
     *PDFUtilListener
     */

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


}