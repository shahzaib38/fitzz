package imagetrack.app.trackobject.customview

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.*
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.common.Constants.CIRCLE_COUNT
import imagetrack.app.trackobject.common.Constants.COLOR_BLUE
import imagetrack.app.trackobject.common.Constants.COLOR_GREEN
import imagetrack.app.trackobject.common.Constants.COLOR_RED
import imagetrack.app.trackobject.common.Constants.COLOR_YELLOW
import imagetrack.app.trackobject.common.Constants.DEFAULT_ANIM_DELAY
import imagetrack.app.trackobject.common.Constants.DEFAULT_ANIM_DISTANCE
import imagetrack.app.trackobject.common.Constants.DEFAULT_ANIM_DURATION
import imagetrack.app.trackobject.common.Constants.DEFAULT_ANIM_INTERPOLATOR
import imagetrack.app.trackobject.common.Constants.DEFAULT_CIRCLE_MARGIN
import imagetrack.app.trackobject.common.Constants.DEFAULT_CIRCLE_RADIUS

class CircleLoadingProgressBar : View {

    private var circleRadius = DEFAULT_CIRCLE_RADIUS
    private var circleMargin = DEFAULT_CIRCLE_MARGIN
    private var animDistance = DEFAULT_ANIM_DISTANCE
    private var animDuration = DEFAULT_ANIM_DURATION
    private var animDelay = DEFAULT_ANIM_DELAY
    private var animInterpolator = DEFAULT_ANIM_INTERPOLATOR

    private var colors = listOf(COLOR_BLUE, COLOR_RED, COLOR_YELLOW, COLOR_GREEN)
    private val positions = mutableListOf(0f, 0f, 0f, 0f)
    private val animatorSet = AnimatorSet()

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.RED
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CircleLoadingProgressBar, 0, 0)
        circleRadius = typedArray.getDimension(R.styleable.CircleLoadingProgressBar_circleRadius, this.circleRadius)
        circleMargin = typedArray.getDimension(R.styleable.CircleLoadingProgressBar_circleMargin, this.circleMargin)
        animDistance = typedArray.getDimension(R.styleable.CircleLoadingProgressBar_animDistance, this.animDistance)
        animDuration = typedArray.getInt(R.styleable.CircleLoadingProgressBar_animDuration, this.animDuration.toInt()).toLong()
        animDelay = typedArray.getInt(R.styleable.CircleLoadingProgressBar_animDelay, this.animDelay.toInt()).toLong()
        animInterpolator = typedArray.getInt(R.styleable.CircleLoadingProgressBar_animInterpolator, this.animInterpolator)
        typedArray.recycle()

        val animators = mutableListOf<Animator>()

        for (i in 0 until CIRCLE_COUNT) {
            animators.add(ObjectAnimator.ofFloat(0f, animDistance).apply {
                this.duration = animDuration
                this.startDelay = i * animDelay
                this.repeatCount = INFINITE
                this.repeatMode = REVERSE
                this.interpolator = when (animInterpolator) {
                    0 -> AccelerateInterpolator()
                    1 -> DecelerateInterpolator()
                    2 -> AccelerateDecelerateInterpolator()
                    3 -> AnticipateInterpolator()
                    4 -> AnticipateOvershootInterpolator()
                    5 -> LinearInterpolator()
                    6 -> OvershootInterpolator()
                    else -> AccelerateDecelerateInterpolator()
                }

                this.addUpdateListener {
                    positions[i] = it.animatedValue as Float
                    invalidate()
                }
            })
        }

        animatorSet.playTogether(animators)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var startPoint = width/2 - ((CIRCLE_COUNT-1) * (circleRadius + circleMargin/2))

        for (i in 0 until CIRCLE_COUNT) {
            paint.color = colors[i]
            canvas.drawCircle(startPoint, height/2f + positions[i], circleRadius, paint)
            startPoint += (circleRadius*2) + circleMargin
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animatorSet.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorSet.end()
    }
}