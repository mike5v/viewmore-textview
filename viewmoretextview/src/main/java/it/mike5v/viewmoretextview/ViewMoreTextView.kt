package it.mike5v.viewmoretextview

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by Michele Quintavalle on 2020-01-15.
 */
class ViewMoreTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    companion object {
        const val ANIMATION_PROPERTY_MAX_HEIGHT = "maxHeight"
        const val ANIMATION_PROPERTY_ALPHA = "alpha"
        const val DEFAULT_ELLIPSIZE_TEXT ="..."
        const val MAX_VALUE_ALPHA = 255
        const val MIN_VALUE_ALPHA = 0
    }

    private var visibleLines: Int? = null
    private var isExpanded: Boolean? = null
    private var animationDuration: Int? = null
    private var foregroundColor: Int? = null

    private var ellipsizeText: String? = DEFAULT_ELLIPSIZE_TEXT
    private var initialValue: String? = null

    init {
        val attributes = context?.obtainStyledAttributes(attrs, R.styleable.ViewMoreTextView)
        visibleLines = attributes?.getInt(R.styleable.ViewMoreTextView_visibleLines, 0)
        isExpanded = attributes?.getBoolean(R.styleable.ViewMoreTextView_isExpanded, false)
        animationDuration = attributes?.getInt(R.styleable.ViewMoreTextView_duration, 1000)
        foregroundColor = attributes?.getColor(R.styleable.ViewMoreTextView_foregroundColor, Color.TRANSPARENT)
        ellipsizeText = attributes?.getString(R.styleable.ViewMoreTextView_ellipsizeText)
        attributes?.recycle()

        if (visibleLines == 0) {
            throw IllegalStateException("You must set visibleLines > 0")
        }

        setMaxLines(isExpanded!!)
        setForeground(isExpanded!!)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (initialValue.isNullOrBlank()) {
            initialValue = text.toString()

            setEllipsizedText(isExpanded!!)
        }
    }

    fun toggle() {
        isExpanded = !isExpanded!!

        if (isExpanded!!)
            setEllipsizedText(isExpanded!!)

        val startHeight = measuredHeight
        setMaxLines(isExpanded!!)
        measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        val endHeight = measuredHeight

        animationSet(startHeight, endHeight).apply {
            duration = animationDuration?.toLong()!!
            start()

            addListener(object : Animator.AnimatorListener {

                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    if (!isExpanded!!)
                        setEllipsizedText(isExpanded!!)
                }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationStart(animation: Animator?) {}

            })
        }
    }

    private fun setEllipsizedText(isExpanded: Boolean) {
        text = if (isExpanded) {
            initialValue
        } else {
            SpannableStringBuilder(
                visibleText().substring(
                    0,
                    visibleText().length - ellipsizeText?.length!!
                )
            ).append(ellipsizeText?.span())

        }
    }

    private fun visibleText(): String {
        val start = layout.getLineStart(0)
        val end = layout.getLineEnd(visibleLines!! - 1)

        return initialValue?.substring(start, end)!!
    }

    private fun String.span(): SpannableString =
        SpannableString(this).apply {
            setSpan(
                ForegroundColorSpan(Color.BLUE),
                0,
                this.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                UnderlineSpan(),
                0,
                this.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

    private fun setMaxLines(isExpanded: Boolean) {
        maxLines = if (!isExpanded) {
            visibleLines!!
        } else {
            Integer.MAX_VALUE
        }
    }

    private fun setForeground(isExpanded: Boolean) {
        foreground = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,
            intArrayOf(foregroundColor!!, Color.TRANSPARENT)
        )
        foreground.alpha = if (isExpanded) {
            MIN_VALUE_ALPHA
        } else {
            MAX_VALUE_ALPHA
        }
    }

    private fun animationSet(startHeight: Int, endHeight: Int): AnimatorSet {
        return AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofInt(
                    this@ViewMoreTextView,
                    ANIMATION_PROPERTY_MAX_HEIGHT,
                    startHeight,
                    endHeight
                ),
                ObjectAnimator.ofInt(
                    this@ViewMoreTextView.foreground,
                    ANIMATION_PROPERTY_ALPHA,
                    foreground.alpha,
                    MAX_VALUE_ALPHA - foreground.alpha
                )
            )
        }
    }

}