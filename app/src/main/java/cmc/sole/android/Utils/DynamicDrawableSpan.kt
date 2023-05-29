package cmc.sole.android.Utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan


//internal class NewDynamicDrawableSpan(context: Context, @DrawableRes resourceId: Int) :
//    DynamicDrawableSpan() {
//    private val mContext: Context
//    private val mResourceId: Int
//
//    init {
//        mContext = context
//        mResourceId = resourceId
//    }
//
//    override fun getDrawable(): Drawable {
//        val drawable: Drawable = mContext.getDrawable(mResourceId)!!
//        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
//        return drawable
//    }
//}


class DynamicDrawableSpan(context: Context, backgroundColor: Int, textColor: Int) :
    ReplacementSpan() {
    private var backgroundColor = 0
    private var textColor = 0

    init {
        this.backgroundColor = backgroundColor
        this.textColor = textColor
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val rect = RectF(x, top.toFloat(), x + measureText(paint, text, start, end), bottom.toFloat())
        paint.color = backgroundColor
        canvas.drawRoundRect(rect, 20f, 20f, paint)
        paint.color = textColor
        canvas.drawText(text, start, end, x, y.toFloat(), paint)
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return Math.round(paint.measureText(text, start, end)).toInt()
    }

    private fun measureText(paint: Paint, text: CharSequence, start: Int, end: Int): Float {
        return paint.measureText(text, start, end)
    }

    companion object {
        private const val CORNER_RADIUS = 8
    }
}