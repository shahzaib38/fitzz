package imagetrack.app.graphics

//
//abstract class Graphic(private val overlay: GraphicOverlay){
//
//
//    abstract fun draw(canvas : Canvas)
//    open fun scale(imagePixel: Float): Float {
//        return imagePixel * overlay.scaleFactor
//    }
//
//
//    open fun getApplicationContext(): Context? {
//        return overlay.context.applicationContext
//    }
//
//    open fun isImageFlipped(): Boolean {
//        return overlay.isImageFlipped
//    }
//
//    open fun translateX(x: Float): Float {
//        return if (overlay.isImageFlipped) {
//            overlay.width - (scale(x) - overlay.postScaleWidthOffset)
//        } else {
//            scale(x) - overlay.postScaleWidthOffset
//        }
//    }
//
//    open fun translateY(y: Float): Float {
//        return scale(y) - overlay.postScaleHeightOffset
//
//    }
//
//    /**
//     * Returns a [Matrix] for transforming from image coordinates to overlay view coordinates.
//     */
//    open fun getTransformationMatrix(): Matrix? {
//        return overlay.transformationMatrix
//    }
//
//    open fun postInvalidate() {
//        overlay.postInvalidate()
//    }
//
//}