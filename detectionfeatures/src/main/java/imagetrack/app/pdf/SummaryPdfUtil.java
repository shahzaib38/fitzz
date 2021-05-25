package imagetrack.app.pdf;


//import android.app.Activity;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import image.app.trackobject.camera_features.R;
//
//public class SummaryPdfUtil {
//
//    // TAG.
//    public static final String TAG = SummaryPdfUtil.class.getName();
//
//    // PDF_PAGE_WIDTH_PX.
//    private static final int PDF_PAGE_WIDTH_PX = 595;
//
//    // PDF_PAGE_HEIGHT_PX.
//    private static final int PDF_PAGE_HEIGHT_PX = 842;
//
//    // MARGIN_TOP_PX.
//    private static final int MARGIN_TOP_PX = 54;
//
//    // MARGIN_LEFT_PX.
//    private static final int MARGIN_LEFT_PX = 54;
//
//    // MARGIN_RIGHT_PX.
//    private static final int MARGIN_RIGHT_PX = 37;
//
//    // MARGIN_BOTTOM_PX.
//    private static final int MARGIN_BOTTOM_PX = 103;
//
//    // sInstance.
//    private static SummaryPdfUtil sInstance;
//
//
//    /**
//     * Constructor.
//     */
//    private SummaryPdfUtil() {
//
//    }
//
//    /**
//     * Get Instance.
//     *
//     * @return Singleton sInstance of SummaryPdfUtil.
//     */
//    public static SummaryPdfUtil getInstance() {
//        if (sInstance == null) {
//            sInstance = new SummaryPdfUtil();
//        }
//        return sInstance;
//    }
//
//    /**
//     * Generate Summary PDF.
//     *
//     * @param activity Activity.
//     */
//    public final void generateSummaryPdf(final SummaryPdf summaryPdf, final String filePath,
//                                         final Activity activity, final SummaryPdfListener listener) {
//
//        // Generate Summary Pdf
//        new GenerateSummaryPdfViewAsync(summaryPdf, filePath, activity, listener).execute();
//    }
//
//
//    /**
//     * Listener used to send Summary PDF Generation callback.
//     */
//    public interface SummaryPdfListener {
//        /**
//         * Called on the success of Generation of summary PDF.
//         */
//        void generateSummaryPdfSuccess();
//
//        /**
//         * Called on the failure of Generation of summary PDF.
//         *
//         * @param exception Exception occurred during PDFGeneration.
//         */
//        void generateSummaryPdfFailure(final Exception exception);
//    }
//
//    /**
//     * Generate Summary PDF View Async.
//     */
//    private class GenerateSummaryPdfViewAsync extends AsyncTask<Void, Void, Boolean>
//            implements PDFUtil.PDFUtilListener {
//
//        // mSummaryPdf.
//        private SummaryPdf mSummaryPdf;
//
//        // mFilePath.
//        private String mFilePath;
//
//        // mContext.
//        private Context mContext;
//
//        // mListener.
//        private SummaryPdfListener mListener;
//
//        // mException.
//        private Exception mException;
//
//        // mInflater.
//        private LayoutInflater mInflater;
//
//        // mPdfPageViews.
//        private List<View> mPdfPageViews = null;
//
//        // mPdfPageView.
//        private ViewGroup mPdfPageView;
//
//        // mPdfPageAvailableHeight
//        private int mPdfPageAvailableHeight;
//
//        /**
//         * Constructor.
//         */
//        public GenerateSummaryPdfViewAsync(final SummaryPdf summaryPdf, final String filePath,
//                                           final Activity activity, final SummaryPdfListener listener) {
//            // Initialize.
//            this.mSummaryPdf = summaryPdf;
//            this.mFilePath = filePath;
//            this.mContext = activity;
//            this.mListener = listener;
//            mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        }
//
//        /**
//         * Do In Background.
//         *
//         * @param params Input Params.
//         * @return True if Generate Summary PDF view is successful, else FALSE.
//         */
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            boolean isGenerateSummaryViewSuccessful;
//            try {
//                // Create List View.
//                mPdfPageViews = new ArrayList<>();
//
//                // Create First Page of our PDF.
//                mPdfPageView = getPdfPageView();
//
//                // Summary Header View.
//                View summaryHeaderView = getSummaryHeaderView();
//                addViewToPage(summaryHeaderView);
//
//                // What you told us View.
//                View whatWeToldUs = getSummaryContentView(mSummaryPdf.getWhatYouToldUs(),
//                        R.drawable.icon_pdf_customer_problem, mContext.getString(R.string.what_you_told));
//                addViewToPage(whatWeToldUs);
//
//                // What We Found View.
//                View whatWeFound = getSummaryContentView(mSummaryPdf.getWhatWeFound(),
//                        R.drawable.icon_pdf_what_we_found, mContext.getString(R.string.what_we_found));
//                addViewToPage(whatWeFound);
//
//                // How We Improved Things View.
//                View howWeImprovedThings = getSummaryContentView(mSummaryPdf.getHowWeImprovedThings(),
//                        R.drawable.icon_pdf_how_we_improved_things, mContext.getString(R.string.how_we_improve));
//                addViewToPage(howWeImprovedThings);
//
//                // Your Internet Speed.
//                View yourInternetSpeed = getSummaryInternetSpeedContentView(mSummaryPdf.getYourInternetSpeed(),
//                        R.drawable.icon_pdf_internet_speed, mContext.getString(R.string.your_internet_speed));
//                addViewToPage(yourInternetSpeed);
//
//                // Your Current Status View.
//                View yourCurrentStatus = getSummaryContentView(mSummaryPdf.getYourCurrentStatus(),
//                        R.drawable.icon_pdf_current_status, mContext.getString(R.string.current_status));
//                addViewToPage(yourCurrentStatus);
//
//                // Your Wifi BlackSpots
//                View yourWifiBlackSpots = getSummaryContentView(mSummaryPdf.getYourWifiBlackSpots(),
//                        R.drawable.icon_pdf_wifi_black_spots, mContext.getString(R.string.wifi_blackspots));
//                addViewToPage(yourWifiBlackSpots);
//
//                // Add view.
//                mPdfPageViews.add(mPdfPageView);
//
//                isGenerateSummaryViewSuccessful = true;
//
//            } catch (Exception exception) {
//                this.mException = exception;
//                Log.e(TAG, exception.getMessage());
//                isGenerateSummaryViewSuccessful = false;
//            }
//            return isGenerateSummaryViewSuccessful;
//        }
//
//        /**
//         * On Post Execute.
//         *
//         * @param isGenerateSummaryViewSuccessful boolean value to check generated summary view is successful.
//         */
//        @Override
//        protected void onPostExecute(Boolean isGenerateSummaryViewSuccessful) {
//            super.onPostExecute(isGenerateSummaryViewSuccessful);
//            if (isGenerateSummaryViewSuccessful) {
//                PDFUtil.getInstance().generatePDF(mPdfPageViews, mFilePath, this);
//            } else {
//                mListener.generateSummaryPdfFailure(mException);
//            }
//        }
//
//        /**
//         * Get PDF Page View.
//         *
//         * @return View.
//         */
//        private ViewGroup getPdfPageView() {
//            ViewGroup pdfPageView = (ViewGroup) mInflater.inflate(R.layout.summary_pdf_page, null);
//            pdfPageView.setPadding(MARGIN_LEFT_PX, MARGIN_TOP_PX, MARGIN_RIGHT_PX, MARGIN_BOTTOM_PX);
//            //setMargins(mPdfPageView, MARGIN_LEFT_PX, MARGIN_TOP_PX, MARGIN_RIGHT_PX, MARGIN_BOTTOM_PX);
//            int measureWidth = View.MeasureSpec.makeMeasureSpec(PDF_PAGE_WIDTH_PX, View.MeasureSpec.EXACTLY);
//            int measuredHeight = View.MeasureSpec.makeMeasureSpec(PDF_PAGE_HEIGHT_PX, View.MeasureSpec.EXACTLY);
//            pdfPageView.measure(measureWidth, measuredHeight);
//            mPdfPageAvailableHeight = PDF_PAGE_HEIGHT_PX;
//            mPdfPageAvailableHeight -= MARGIN_TOP_PX + MARGIN_BOTTOM_PX;
//            return pdfPageView;
//        }
//
//        /**
//         * Get Summary Header View.
//         *
//         * @return Summary Header View.
//         */
//        private View getSummaryHeaderView() {
//            View summaryHeaderView = mInflater.inflate(R.layout.summary_pdf_title, null);
//            int measureWidth = View.MeasureSpec.makeMeasureSpec(PDF_PAGE_WIDTH_PX, View.MeasureSpec.EXACTLY);
//            int measuredHeight = View.MeasureSpec.UNSPECIFIED;
//            summaryHeaderView.measure(measureWidth, measuredHeight);
//            return summaryHeaderView;
//        }
//
//        /**
//         * Get Summary Content View.
//         *
//         * @return Summary Content View.
//         */
//        private View getSummaryContentView(final List<String> summaryContentList, final int titleIcon,
//                                           final String titleText) {
//            View summaryContentView = mInflater.inflate(R.layout.summary_pdf_content, null);
//            // Title Icon.
//            ImageView imageViewTitleIcon = (ImageView) summaryContentView.
//                    findViewById(R.id.SummaryPdfContentImageViewTitleIcon);
//            imageViewTitleIcon.setImageResource(titleIcon);
//            // Title Text.
//            TextView textViewTitleText = (TextView) summaryContentView.
//                    findViewById(R.id.SummaryPdfContentTextViewTitleText);
//            textViewTitleText.setText(titleText);
//            // Title Content.
//            TextView textViewContentText = (TextView) summaryContentView.
//                    findViewById(R.id.SummaryPdfContentTextViewTitleContent);
//            textViewContentText.setVisibility(View.GONE);
//            // Content.
//            ViewGroup contentViewGroup = (ViewGroup) summaryContentView.
//                    findViewById(R.id.SummaryPdfContentLinearLayoutContent);
//            if (summaryContentList != null && summaryContentList.size() > 0) {
//                for (String whatYouToldUs : summaryContentList) {
//                    TextView textViewContent = (TextView) mInflater.inflate(R.layout.summary_pdf_content_text, null);
//                    textViewContent.setText(whatYouToldUs);
//                    contentViewGroup.addView(textViewContent);
//                }
//            }
//            int measureWidth = View.MeasureSpec.makeMeasureSpec(PDF_PAGE_WIDTH_PX, View.MeasureSpec.EXACTLY);
//            int measuredHeight = View.MeasureSpec.UNSPECIFIED;
//            summaryContentView.measure(measureWidth, measuredHeight);
//            return summaryContentView;
//        }
//
//        /**
//         * Get Summary Content View.
//         *
//         * @return Summary Content View.
//         */
//        private View getSummaryInternetSpeedContentView(final String summaryContent, final int titleIcon,
//                                                        final String titleText) {
//            View summaryContentView = mInflater.inflate(R.layout.summary_pdf_content, null);
//            // Title Icon.
//            ImageView imageViewTitleIcon = (ImageView) summaryContentView.
//                    findViewById(R.id.SummaryPdfContentImageViewTitleIcon);
//            imageViewTitleIcon.setImageResource(titleIcon);
//            // Title Text.
//            TextView textViewTitleText = (TextView) summaryContentView.
//                    findViewById(R.id.SummaryPdfContentTextViewTitleText);
//            textViewTitleText.setText(titleText);
//            // Title Content.
//            TextView textViewContentText = (TextView) summaryContentView.
//                    findViewById(R.id.SummaryPdfContentTextViewTitleContent);
//            textViewContentText.setText(summaryContent);
//            // Content.
//            ViewGroup contentViewGroup = (ViewGroup) summaryContentView.
//                    findViewById(R.id.SummaryPdfContentLinearLayoutContent);
//            contentViewGroup.setVisibility(View.GONE);
//            int measureWidth = View.MeasureSpec.makeMeasureSpec(PDF_PAGE_WIDTH_PX, View.MeasureSpec.EXACTLY);
//            int measuredHeight = View.MeasureSpec.UNSPECIFIED;
//            summaryContentView.measure(measureWidth, measuredHeight);
//            return summaryContentView;
//        }
//
//        /**
//         * Add View to Page.
//         *
//         * @param view view.
//         */
//        private void addViewToPage(final View view) {
//
//            // Check if we have height to append view to page.
//            int measuredHeight = view.getMeasuredHeight();
//            if (mPdfPageAvailableHeight > measuredHeight) {
//                // We have available height.
//                mPdfPageView.addView(view);
//                mPdfPageAvailableHeight -= measuredHeight;
//            } else {
//                // We don't have available height.
//                // Add previous page to list.
//                mPdfPageViews.add(mPdfPageView);
//
//                // Create new page.
//                mPdfPageView = getPdfPageView();
//
//                // Add view to page.
//                mPdfPageView.addView(view);
//                mPdfPageAvailableHeight -= measuredHeight;
//            }
//        }
//
//        /**
//         * PDF Util Listener.
//         * Call on the success of PDF Generation.
//         */
//        @Override
//        public void pdfGenerationSuccess() {
//            mListener.generateSummaryPdfSuccess();
//        }
//
//        /**
//         * Called on the Failure of PDF Generation.
//         *
//         * @param exception Exception occurred during PDFGeneration.
//         */
//        @Override
//        public void pdfGenerationFailure(Exception exception) {
//            mListener.generateSummaryPdfFailure(exception);
//        }
//    }
//
//}