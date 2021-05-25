//package image.app.pdf;
//
//import android.annotation.TargetApi;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.pdf.PdfDocument;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.util.Log;
//import android.view.View;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//@TargetApi(19)
//public class PDFUtil {
//
//
//    /**
//     * TAG.
//     */
//    private static final String TAG = PDFUtil.class.getName();
//    /**
//     * Page width for our PDF.
//     */
//    public static final double PDF_PAGE_WIDTH = 8.3 * 72;
//    /**
//     * Page height for our PDF.
//     */
//    public static final double PDF_PAGE_HEIGHT = 11.7 * 72;
//    /**
//     * Page width for our PDF in inch.
//     */
////    public static final double PDF_PAGE_WIDTH_INCH = 8.3;
//    /**
//     * Page height for our PDF in inch.
//     */
////    public static final double PDF_PAGE_HEIGHT_INCH = 11.7;
//    /**
//     * Singleton instance for PDFUtil.
//     */
//    private static PDFUtil sInstance;
//
//    /**
//     * Constructor.
//     */
//    private PDFUtil() {
//
//    }
//
//    /**
//     * Return singleton instance of PDFUtil.
//     *
//     * @return singleton instance of PDFUtil.
//     */
//    public static PDFUtil getInstance() {
//        if (sInstance == null) {
//            sInstance = new PDFUtil();
//        }
//        return sInstance;
//    }
//
//    public final void generatePDF(final String filePath,
//                                  final PDFUtilListener listener) {
//        //Check Api Version.
//        int currentApiVersion = Build.VERSION.SDK_INT;
//        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
//            // Kitkat
//            new GeneratePDFAsync( filePath, listener).execute();
//        } else {
//            // Before Kitkat
//            Log.e(TAG, "Generate PDF is not available for your android version.");
//            listener.pdfGenerationFailure(
//                    new APINotSupportedException("Generate PDF is not available for your android version."));
//        }
//
//    }
//
//
//    /**
//     * Listener used to send PDF Generation callback.
//     */
//    public interface PDFUtilListener {
//        /**
//         * Called on the success of PDF Generation.
//         */
//        void pdfGenerationSuccess();
//
//        /**
//         * Called when PDF Generation failed.
//         *
//         * @param exception Exception occurred during PDFGeneration.
//         */
//        void pdfGenerationFailure(final Exception exception);
//    }
//
//    /**
//     * Async task class used to generate PDF in separate thread.
//     */
//    private class GeneratePDFAsync extends AsyncTask<String, Void, Boolean> {
//
//        // mContentViews.
//        private List<View> mContentViews;
//
//        // mFilePath.
//        private String mFilePath;
//
//        // mListener.
//        private PDFUtilListener mListener = null;
//
//        // mException.
//        private Exception mException;
//
//
//        public GeneratePDFAsync( final String filePath, final PDFUtilListener listener) {
//            this.mFilePath = filePath;
//            this.mListener = listener;
//        }
//
//
//        /**
//         * Do In Background.
//         *
//         * @param params Params
//         * @return TRUE if PDF successfully generated else FALSE.
//         */
//        @Override
//        protected Boolean doInBackground(String... params) {
//            boolean isPdfSuccessfullyGenerated;
//            try {
//                // Create PDF Document.
//                PdfDocument pdfDocument = new PdfDocument();
//
//                // Write content to PDFDocument.
//                writePDFDocument(params[0] ,pdfDocument);
//
//                // Save document to file.
//                savePDFDocumentToStorage(pdfDocument);
//
//                isPdfSuccessfullyGenerated = true;
//            } catch (Exception exception) {
//                Log.e(TAG, exception.getMessage());
//                isPdfSuccessfullyGenerated = false;
//            }
//            return isPdfSuccessfullyGenerated;
//        }
//
//        /**
//         * On Post Execute.
//         *
//         * @param isPdfSuccessfullyGenerated boolean value to check if the pdf is successfully generated.
//         */
//        @Override
//        protected void onPostExecute(Boolean isPdfSuccessfullyGenerated) {
//            super.onPostExecute(isPdfSuccessfullyGenerated);
//            if (isPdfSuccessfullyGenerated) {
//                //Send Success callback.
//                mListener.pdfGenerationSuccess();
//            } else {
//                //Send Error callback.
//                mListener.pdfGenerationFailure(mException);
//            }
//        }
//
//        /**
//         * Writes given PDFDocument using content views.
//         *
//         * @param pdfDocument PDFDocument to be written.
//         */
//        private void writePDFDocument(String  data  ,PdfDocument pdfDocument) {
//
//
//    //        for (int i = 0; i < mContentViews.size(); i++) {
//
//                //Get Content View.
//  //              View contentView = mContentViews.get(i);
//
//                // crate a page description
//                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.
//                        Builder((int) PDF_PAGE_WIDTH, (int) PDF_PAGE_HEIGHT,  1).create();
//
//                // start a page
//                PdfDocument.Page page = pdfDocument.startPage(pageInfo);
//
//                // draw view on the page
//                Canvas pageCanvas = page.getCanvas();
//                int pageWidth = pageCanvas.getWidth();
//                int pageHeight = pageCanvas.getHeight();
//                int measureWidth = View.MeasureSpec.makeMeasureSpec(pageWidth, View.MeasureSpec.EXACTLY);
//                int measuredHeight = View.MeasureSpec.makeMeasureSpec(pageHeight, View.MeasureSpec.EXACTLY);
//           //     contentView.measure(measureWidth, measuredHeight);
//         //       contentView.layout(0, 0, pageWidth, pageHeight);
//                Paint paint = new Paint();
//                pageCanvas.drawText(data ,10, 10 ,paint);
//
//
//                // finish the page
//                pdfDocument.finishPage(page);
//
//      //      }
//        }
//
//        /**
//         * Save PDFDocument to the File in the storage.
//         *
//         * @param pdfDocument Document to be written to the Storage.
//         * @throws java.io.IOException
//         */
//        private void savePDFDocumentToStorage(final PdfDocument pdfDocument) throws IOException {
//            FileOutputStream fos = null;
//            // Create file.
//            File pdfFile = new File(mFilePath);
//
//            //Create parent directories
//            File parentFile = pdfFile.getParentFile();
//            if (!parentFile.exists() && !parentFile.mkdirs()) {
//                throw new IllegalStateException("Couldn't create directory: " + parentFile);
//            }
//            boolean fileExists = pdfFile.exists();
//            // If File already Exists. delete it.
//            if (fileExists) {
//                fileExists = !pdfFile.delete();
//            }
//            try {
//                if (!fileExists) {
//                    // Create New File.
//                    fileExists = pdfFile.createNewFile();
//                }
//
//                if (fileExists) {
//                    // Write PDFDocument to the file.
//                    fos = new FileOutputStream(pdfFile);
//                    pdfDocument.writeTo(fos);
//
//                    //Close output stream
//                    fos.close();
//
//                    // close the document
//                    pdfDocument.close();
//                }
//            } catch (IOException exception) {
//                Log.d(this.getClass().getName(), "Exception", exception);
//                if (fos != null) {
//                    fos.close();
//                }
//                throw exception;
//            }
//        }
//    }
//
//    /**
//     * APINotSupportedException will be thrown If the device doesn't support PDF methods.
//     */
//    private static class APINotSupportedException extends Exception {
//        // mErrorMessage.
//        private String mErrorMessage;
//
//        /**
//         * Constructor.
//         *
//         * @param errorMessage Error Message.
//         */
//        public APINotSupportedException(final String errorMessage) {
//            this.mErrorMessage = errorMessage;
//        }
//
//        /**
//         * To String.
//         *
//         * @return error message as a string.
//         */
//        @Override
//        public String toString() {
//            return "APINotSupportedException{" +
//                    "mErrorMessage='" + mErrorMessage + '\'' +
//                    '}';
//        }
//    }
//}