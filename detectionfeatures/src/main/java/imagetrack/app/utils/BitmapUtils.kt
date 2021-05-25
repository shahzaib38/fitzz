package imagetrack.app.utils

import android.graphics.*
import android.media.Image
import android.media.Image.Plane
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


object BitmapUtils {


        private const val TAG = "BitmapUtils"
        /** Converts NV21 format byte buffer to bitmap.  */

        fun getBitmap(data: ByteBuffer, metadata: FrameMetadata): Bitmap? {
            data.rewind()
            val imageInBuffer = ByteArray(data.limit())
            data[imageInBuffer, 0, imageInBuffer.size]
            try {
                val image = YuvImage(
                    imageInBuffer, ImageFormat.NV21, metadata.getWidth(), metadata.getHeight(), null
                )
                val stream = ByteArrayOutputStream()
                image.compressToJpeg(
                    Rect(
                        0,
                        0,
                        metadata.getWidth(),
                        metadata.getHeight()
                    ), 80, stream
                )
                val bmp =
                    BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size())
                stream.close()
                return rotateBitmap(bmp, metadata.getRotation(), false, false)
            } catch (e: Exception) {
                Log.e("VisionProcessorBase", "Error: " + e.message)
            }
            return null
        }


        private fun rotateBitmap(
            bitmap: Bitmap, rotationDegrees: Int, flipX: Boolean, flipY: Boolean
        ): Bitmap? {
            val matrix = Matrix()
            // Rotate the image back to straight.
            matrix.postRotate(rotationDegrees.toFloat())
            // Mirror the image along the X or Y axis.
            matrix.postScale(if (flipX) -1.0f else 1.0f, if (flipY) -1.0f else 1.0f)
            val rotatedBitmap = Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )
            // Recycle the old bitmap if it has changed.
            if (rotatedBitmap != bitmap) {
                bitmap.recycle()
            }
            return rotatedBitmap
        }


        /** Converts a YUV_420_888 image from CameraX API to a bitmap.  */
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        @ExperimentalGetImage
        fun getBitmap(image: ImageProxy): Bitmap? {
            val frameMetadata: FrameMetadata = FrameMetadata.Builder()
                .setWidth(image.getWidth())
                .setHeight(image.getHeight())
                .setRotation(image.getImageInfo().getRotationDegrees())
                .build()
            val nv21Buffer: ByteBuffer = yuv420ThreePlanesToNV21(image.getImage()!!.getPlanes(), image.getWidth(), image.getHeight())
            return getBitmap(nv21Buffer, frameMetadata)
        }

        private fun yuv420ThreePlanesToNV21(
            yuv420888planes: Array<Image.Plane>,
            width: Int,
            height: Int
        ): ByteBuffer {

            val imageSize = width * height
            val out = ByteArray(imageSize + 2 * (imageSize / 4))

            if (areUVPlanesNV21(yuv420888planes, width, height)) {
                // Copy the Y values.
                yuv420888planes[0].getBuffer().get(out, 0, imageSize);

                val uBuffer = yuv420888planes[1].getBuffer();
                val vBuffer = yuv420888planes[2].getBuffer();
                // Get the first V value from the V buffer, since the U buffer does not contain it.
                vBuffer.get(out, imageSize, 1);
                // Copy the first U value and the remaining VU values from the U buffer.
                uBuffer.get(out, imageSize + 1, 2 * imageSize / 4 - 1);
            } else {
                // Fallback to copying the UV values one by one, which is slower but also works.
                // Unpack Y.
                unpackPlane(yuv420888planes[0], width, height, out, 0, 1);
                // Unpack U.
                unpackPlane(yuv420888planes[1], width, height, out, imageSize + 1, 2);
                // Unpack V.
                unpackPlane(yuv420888planes[2], width, height, out, imageSize, 2);
            }

            return ByteBuffer.wrap(out);

        }

        private fun unpackPlane(
            plane: Plane,
            width: Int,
            height: Int,
            out: ByteArray,
            offset: Int,
            pixelStride: Int
        ) {
            val buffer = plane.buffer
            buffer.rewind()
            // Compute the size of the current plane.
// We assume that it has the aspect ratio as the original image.
            val numRow = (buffer.limit() + plane.rowStride - 1) / plane.rowStride
            if (numRow == 0) {
                return
            }
            val scaleFactor = height / numRow
            val numCol = width / scaleFactor
            // Extract the data in the output buffer.
            var outputPos = offset
            var rowStart = 0
            for (row in 0 until numRow) {
                var inputPos = rowStart
                for (col in 0 until numCol) {
                    out[outputPos] = buffer[inputPos]
                    outputPos += pixelStride
                    inputPos += plane.pixelStride
                }
                rowStart += plane.rowStride
            }
        }


        private fun areUVPlanesNV21(
            planes: Array<Plane>,
            width: Int,
            height: Int
        ): Boolean {
            val imageSize = width * height
            val uBuffer = planes[1].buffer
            val vBuffer = planes[2].buffer
            // Backup buffer properties.
            val vBufferPosition = vBuffer.position()
            val uBufferLimit = uBuffer.limit()
            // Advance the V buffer by 1 byte, since the U buffer will not contain the first V value.
            vBuffer.position(vBufferPosition + 1)
            // Chop off the last byte of the U buffer, since the V buffer will not contain the last U value.
            uBuffer.limit(uBufferLimit - 1)
            // Check that the buffers are equal and have the expected number of elements.
            val areNV21 =
                vBuffer.remaining() == 2 * imageSize / 4 - 2 && vBuffer.compareTo(uBuffer) == 0
            // Restore buffers to their initial state.
            vBuffer.position(vBufferPosition)
            uBuffer.limit(uBufferLimit)
            return areNV21
        }



}