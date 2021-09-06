
package com.example.psmart

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.google.android.gms.vision.CameraSource
import java.util.HashSet


class GraphicOverlay(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private val mLock = Any()
    private var mPreviewWidth = 0
    private var mWidthScaleFactor = 1.0f
    private var mPreviewHeight = 0
    private var mHeightScaleFactor = 1.0f
    private var mFacing = CameraSource.CAMERA_FACING_BACK
    private val mGraphics: MutableSet<Graphic> = HashSet()

    /**
     * Base class for a custom graphics object to be rendered within the graphic overlay.  Subclass
     * this and implement the [Graphic.draw] method to define the
     * graphics element.  Add instances to the overlay using [GraphicOverlay.add].
     */
    abstract class Graphic(private val mOverlay: GraphicOverlay) {

        abstract fun draw(canvas: Canvas?)


        fun scaleX(horizontal: Float): Float {
            return horizontal * mOverlay.mWidthScaleFactor
        }

        /**
         * Adjusts a vertical value of the supplied value from the preview scale to the view scale.
         */
        fun scaleY(vertical: Float): Float {
            return vertical * mOverlay.mHeightScaleFactor
        }

        /**
         * Adjusts the x coordinate from the preview's coordinate system to the view coordinate
         * system.
         */
        fun translateX(x: Float): Float {
            return if (mOverlay.mFacing == CameraSource.CAMERA_FACING_FRONT) {
                mOverlay.width - scaleX(x)
            } else {
                scaleX(x)
            }
        }

        /**
         * Adjusts the y coordinate from the preview's coordinate system to the view coordinate
         * system.
         */
        fun translateY(y: Float): Float {
            return scaleY(y)
        }

        fun postInvalidate() {
            mOverlay.postInvalidate()
        }
    }

    /**
     * Removes all graphics from the overlay.
     */
    fun clear() {
        synchronized(mLock) { mGraphics.clear() }
        postInvalidate()
    }

    /**
     * Adds a graphic to the overlay.
     */
    fun add(graphic: Graphic) {
        synchronized(mLock) { mGraphics.add(graphic) }
        postInvalidate()
    }

    /**
     * Removes a graphic from the overlay.
     */
    fun remove(graphic: Graphic) {
        synchronized(mLock) { mGraphics.remove(graphic) }
        postInvalidate()
    }

    /**
     * Sets the camera attributes for size and facing direction, which informs how to transform
     * image coordinates later.
     */
    fun setCameraInfo(previewWidth: Int, previewHeight: Int, facing: Int) {
        synchronized(mLock) {
            mPreviewWidth = previewWidth
            mPreviewHeight = previewHeight
            mFacing = facing
        }
        postInvalidate()
    }

    /**
     * Draws the overlay with its associated graphic objects.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        synchronized(mLock) {
            if (mPreviewWidth != 0 && mPreviewHeight != 0) {
                mWidthScaleFactor = canvas.width.toFloat() / mPreviewWidth.toFloat()
                mHeightScaleFactor = canvas.height.toFloat() / mPreviewHeight.toFloat()
            }
            for (graphic in mGraphics) {
                graphic.draw(canvas)
            }
        }
    }
}
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.Surface.ROTATION_0
//import android.view.Surface.ROTATION_90
//import androidx.camera.core.ImageAnalysis
//import androidx.camera.core.ImageProxy
//import com.google.android.gms.vision.Frame.ROTATION_0
//import com.google.android.gms.vision.face.FaceDetector.ALL_CLASSIFICATIONS
//import com.google.android.gms.vision.face.FaceDetector.ALL_LANDMARKS
//
//class RegisterFaceActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register_face)
//
//        // High-accuracy landmark detection and face classification
//        val highAccuracyOpts = FirebaseVisionFaceDetectorOptions.Builder()
//            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
//            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
//            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
//            .build()
//
//// Real-time contour detection of multiple faces
//        val realTimeOpts = FirebaseVisionFaceDetectorOptions.Builder()
//            .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
//            .build()
//    }
//
//}
//
//private fun Any.build() {
//
//}
//
//class FirebaseVisionFaceDetectorOptions {
//    class Builder {
//        fun setPerformanceMode(accurate: FirebaseVisionFaceDetectorOptions.ACCURATE) {
//
//        }
//
//        fun setContourMode(allContours: ALL_CONTOURS): Any {
//            TODO("Not yet implemented")
//        }
//
//    }
//
//    object ACCURATE {
//
//    }
//
//    object ALL_LANDMARKS {
//
//    }
//
//    object ALL_CONTOURS {
//
//    }
//
//    object ALL_CLASSIFICATIONS {
//
//    }
//    private class YourImageAnalyzer : ImageAnalysis.Analyzer {
//        private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
//            0 -> FirebaseVisionImageMetadata.ROTATION_0
//            90 -> FirebaseVisionImageMetadata.ROTATION_90
//            180 -> FirebaseVisionImageMetadata.ROTATION_180
//            270 -> FirebaseVisionImageMetadata.ROTATION_270
//            else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
//        }
//
//        override fun analyze(image: ImageProxy) {
//            val imageProxy = null
//            val mediaImage = imageProxy?.image
//            val imageRotation = degreesToFirebaseRotation(degrees)
//            if (mediaImage != null) {
//                val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
//                // Pass image to an ML Kit Vision API
//                // ...
//            }
//        }
//
//        override fun analyze(image: ImageProxy) {
//            TODO("Not yet implemented")
//        }
//    }
//
//}
//
//class FirebaseVisionImageMetadata {
//
//}
//
//class FirebaseVisionImage {
//
//}
