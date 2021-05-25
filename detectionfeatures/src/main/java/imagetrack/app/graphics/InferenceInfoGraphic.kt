package imagetrack.app.graphics


//
//class InferenceInfoGraphic(
//    private val overlay: GraphicOverlay,
//    private val frameLatency: Long,
//    private val detectorLatency: Long,
//    // Only valid when a stream of input images is being processed. Null for single image mode.
//    @field:Nullable @param:Nullable private val framesPerSecond: Int?
//) : Graphic(overlay) {
//
//    private val textPaint = Paint()
//
//    init {
//        textPaint.color = TEXT_COLOR
//        textPaint.textSize = TEXT_SIZE
//        postInvalidate()
//    }
//
//
//    @Synchronized
//  override  fun draw(canvas: Canvas) {
//        val x = TEXT_SIZE * 0.5f
//        val y = TEXT_SIZE * 1.5f
//        canvas.drawText(
//            "InputImage size: " + overlay.getImageHeight() + "x" + overlay.getImageWidth(),
//            x,
//            y,
//            textPaint
//        )
//        // Draw FPS (if valid) and inference latency
//        if (framesPerSecond != null) {
//            canvas.drawText(
//                "FPS: $framesPerSecond, Frame latency: $frameLatency ms",
//                x,
//                y + TEXT_SIZE,
//                textPaint
//            )
//            canvas.drawText(
//                "Detector latency: $detectorLatency ms",
//                x,
//                y + TEXT_SIZE * 2,
//                textPaint
//            )
//        } else {
//            canvas.drawText(
//                "Frame latency: $frameLatency ms",
//                x,
//                y + TEXT_SIZE,
//                textPaint
//            )
//            canvas.drawText(
//                "Detector latency: $detectorLatency ms",
//                x,
//                y + TEXT_SIZE * 2,
//                textPaint
//            )
//        }
//    }
//
//    companion object {
//        private val TEXT_COLOR: Int = Color.WHITE
//        private const val TEXT_SIZE = 60.0f
//    }
//
//
//}