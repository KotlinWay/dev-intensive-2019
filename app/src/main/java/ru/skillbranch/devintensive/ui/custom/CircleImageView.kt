package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import ru.skillbranch.devintensive.R


import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.Dimension


class CircleImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    var borderColor = CV_BORDER_COLOR
    var borderWidth = CV_BORDER_WIDTH

    private val defaultTextSizePercentage: Int = 0
    private val textSizePercentage: Int = 0

    private var viewSize: Int = 0
    private var drawable0: Drawable? = null

    var circleRadius: Int = 0
    var circleCenterXValue: Int = 0
    var circleCenterYValue: Int = 0

    private val borderPaint = Paint()
    private val mainPaint = Paint()
    private var circleRect: Rect? = null

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, CV_BORDER_COLOR)
            borderWidth = a.getDimension(R.styleable.CircleImageView_cv_borderWidth, CV_BORDER_WIDTH)
            a.recycle()
        }
        initView(context, attrs)
    }

    companion object {
        private const val CV_BORDER_COLOR = Color.WHITE
        private const val CV_BORDER_WIDTH = 2f
    }

    public override fun onDraw(canvas: Canvas) {
        saveBasicValues(canvas)

        if (viewSize === 0) {
            return
        }

        val bitmap = cutIntoCircle(drawableToBitmap(drawable0)) ?: return

        canvas.translate(circleCenterXValue.toFloat(), circleCenterYValue.toFloat())

        //Draw Border
        canvas.drawCircle(circleRadius + borderWidth, circleRadius + borderWidth, circleRadius + borderWidth, borderPaint)

        canvas.drawBitmap(bitmap, 0f, 0f, null)
    }

    fun textSizePercentage(): Int {
        return textSizePercentage
    }

    private fun initView(context: Context, attrs: AttributeSet?) {

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.CircleImageView,
                    0, 0)
            try {
                configureBorderValues(typedArray)
            } finally {
                typedArray.recycle()
            }
        }

        borderPaint.isAntiAlias = true
        borderPaint.style = Paint.Style.FILL
        borderPaint.color = borderColor

        mainPaint.isAntiAlias = true
        mainPaint.color = resources.getColor(R.color.primary_material_dark)
        mainPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }

    private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        return try {
            val bitmap = Bitmap.createBitmap(viewSize, viewSize, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, viewSize, viewSize)
            drawable.draw(canvas)

            bitmap
        } catch (error: OutOfMemoryError) {
            null
        }

    }

    private fun cutIntoCircle(bitmap: Bitmap?): Bitmap? {
        if (bitmap == null) {
            return null
        }

        try {
            val output = Bitmap.createBitmap(viewSize, viewSize, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(output)

            canvas.drawARGB(0, 0, 0, 0)
            canvas.drawCircle(circleRadius + borderWidth, circleRadius + borderWidth, circleRadius.toFloat(), borderPaint)

            canvas.drawBitmap(bitmap, circleRect, circleRect, mainPaint)
            return output
        } catch (error: OutOfMemoryError) {
            return null
        }

    }


    private fun configureBorderValues(typedArray: TypedArray) {
        borderColor = typedArray.getColor(R.styleable.CircleImageView_cv_borderColor, CV_BORDER_COLOR)
        borderWidth = typedArray.getDimension(R.styleable.CircleImageView_cv_borderWidth, CV_BORDER_WIDTH)
//        textSizePercentage = typedArray.getInt(R.styleable.AvatarView_av_text_size_percentage, defaultTextSizePercentage)
    }

    private fun saveBasicValues(canvas: Canvas) {
        val viewHeight = canvas.height
        val viewWidth = canvas.width

        viewSize = Math.min(viewWidth, viewHeight)

        circleCenterXValue = (viewWidth - viewSize) / 2
        circleCenterYValue = (viewHeight - viewSize) / 2
        circleRadius = ((viewSize - borderWidth * 2) / 2).toInt()

        circleRect = Rect(0, 0, viewSize, viewSize)

        maximizeAvailableBorderSize()

        if (viewSize !== 0) {
            drawable0 = drawable
        }
    }

    fun maximizeAvailableBorderSize() {
        if (viewSize / 3 < borderWidth) {
            borderWidth = viewSize / 3.toFloat()
        }
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    @Dimension
    fun getBorderWidth() = borderWidth.toInt()


    fun setBorderWidth(@Dimension dp:Int){
        borderWidth = dp.toFloat()
    }

    fun setBorderColor(hex:String){
        borderColor = Color.parseColor(hex)
    }

}