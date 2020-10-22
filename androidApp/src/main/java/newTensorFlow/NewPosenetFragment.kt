package newTensorFlow

import android.graphics.*
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tensorflowmultiplatform.androidApp.R
import domain.useCases.CameraHandler
import domain.useCases.EndSessionUseCase
import domain.useCases.RunModelUseCase
import domain.useCases.StartSessionUseCase
import originalTensorflow.BodyPart
import originalTensorflow.MODEL_HEIGHT
import originalTensorflow.MODEL_WIDTH
import originalTensorflow.Person

class NewPosenetFragment : Fragment(),
    ActivityCompat.OnRequestPermissionsResultCallback{

    private lateinit var posenetViewModel: PosenetViewModel
    /** Paint class holds the style and color information to draw geometries,text and bitmaps. */
    private var paint = Paint()
    /** List of body joints that should be connected.    */
    private val bodyJoints = listOf(
        Pair(BodyPart.LEFT_WRIST, BodyPart.LEFT_ELBOW),
        Pair(BodyPart.LEFT_ELBOW, BodyPart.LEFT_SHOULDER),
        Pair(BodyPart.LEFT_SHOULDER, BodyPart.RIGHT_SHOULDER),
        Pair(BodyPart.RIGHT_SHOULDER, BodyPart.RIGHT_ELBOW),
        Pair(BodyPart.RIGHT_ELBOW, BodyPart.RIGHT_WRIST),
        Pair(BodyPart.LEFT_SHOULDER, BodyPart.LEFT_HIP),
        Pair(BodyPart.LEFT_HIP, BodyPart.RIGHT_HIP),
        Pair(BodyPart.RIGHT_HIP, BodyPart.RIGHT_SHOULDER),
        Pair(BodyPart.LEFT_HIP, BodyPart.LEFT_KNEE),
        Pair(BodyPart.LEFT_KNEE, BodyPart.LEFT_ANKLE),
        Pair(BodyPart.RIGHT_HIP, BodyPart.RIGHT_KNEE),
        Pair(BodyPart.RIGHT_KNEE, BodyPart.RIGHT_ANKLE)
    )
    /** Threshold for confidence score. */
    private val minConfidence = 0.5

    /** Radius of circle used to draw keypoints.  */
    private val circleRadius = 8.0f

    /** A [SurfaceView] for camera preview.   */
    private var surfaceView: SurfaceView? = null

    /** Abstract interface to someone holding a display surface.    */
    private var surfaceHolder: SurfaceHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        posenetViewModel = ViewModelProvider(requireActivity()).get(PosenetViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_posenet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        surfaceView = view.findViewById(R.id.surface_view)
        surfaceHolder = surfaceView!!.holder

        getView()?.findViewById<Button>(R.id.setup_model)?.setOnClickListener { posenetViewModel.setupSession() }
        getView()?.findViewById<Button>(R.id.run_model)?.setOnClickListener { posenetViewModel.runModel() }
        getView()?.findViewById<Button>(R.id.stop_model)?.setOnClickListener { posenetViewModel.stopSession() }
        posenetViewModel.drawingContent.observe(viewLifecycleOwner) { draw(surfaceHolder!!.lockCanvas(), it.person, it.bitmap) }
    }




    /** Draw bitmap on Canvas.   */
    private fun draw(canvas: Canvas, person: Person, bitmap: Bitmap) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        // Draw `bitmap` and `person` in square canvas.
        val screenWidth: Int
        val screenHeight: Int
        val left: Int
        val right: Int
        val top: Int
        val bottom: Int
        if (canvas.height > canvas.width) {
            screenWidth = canvas.width
            screenHeight = canvas.width
            left = 0
            top = (canvas.height - canvas.width) / 2
        } else {
            screenWidth = canvas.height
            screenHeight = canvas.height
            left = (canvas.width - canvas.height) / 2
            top = 0
        }
        right = left + screenWidth
        bottom = top + screenHeight

        setPaint()
        canvas.drawBitmap(
            bitmap,
            Rect(0, 0, bitmap.width, bitmap.height),
            Rect(left, top, right, bottom),
            paint
        )

        val widthRatio = screenWidth.toFloat() / MODEL_WIDTH
        val heightRatio = screenHeight.toFloat() / MODEL_HEIGHT

        // Draw key points over the image.
        for (keyPoint in person.keyPoints) {
            if (keyPoint.score > minConfidence) {
                val position = keyPoint.position
                val adjustedX: Float = position.x.toFloat() * widthRatio + left
                val adjustedY: Float = position.y.toFloat() * heightRatio + top
                canvas.drawCircle(adjustedX, adjustedY, circleRadius, paint)
            }
        }

        for (line in bodyJoints) {
            if (
                (person.keyPoints[line.first.ordinal].score > minConfidence) and
                (person.keyPoints[line.second.ordinal].score > minConfidence)
            ) {
                canvas.drawLine(
                    person.keyPoints[line.first.ordinal].position.x.toFloat() * widthRatio + left,
                    person.keyPoints[line.first.ordinal].position.y.toFloat() * heightRatio + top,
                    person.keyPoints[line.second.ordinal].position.x.toFloat() * widthRatio + left,
                    person.keyPoints[line.second.ordinal].position.y.toFloat() * heightRatio + top,
                    paint
                )
            }
        }

//        canvas.drawText(
//            "Score: %.2f".format(person.score),
//            (15.0f * widthRatio),
//            (30.0f * heightRatio + bottom),
//            paint
//        )
//        canvas.drawText(
//            "Device: %s".format(posenet.device),
//            (15.0f * widthRatio),
//            (50.0f * heightRatio + bottom),
//            paint
//        )
//        canvas.drawText(
//            "Time: %.2f ms".format(posenet.lastInferenceTimeNanos * 1.0f / 1_000_000),
//            (15.0f * widthRatio),
//            (70.0f * heightRatio + bottom),
//            paint
//        )

        // Draw!
        surfaceHolder!!.unlockCanvasAndPost(canvas)
    }

    /** Set the paint color and size.    */
    private fun setPaint() {
        paint.color = Color.RED
        paint.textSize = 80.0f
        paint.strokeWidth = 8.0f
    }
}