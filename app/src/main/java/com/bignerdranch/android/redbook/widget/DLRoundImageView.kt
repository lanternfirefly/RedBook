package com.bignerdranch.android.redbook.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bignerdranch.android.redbook.R
import kotlin.math.ceil


class DLRoundImageView : AppCompatImageView {
    /*
    * 不能使用xml填充的图片
    * android.graphics.drawable.VectorDrawable cannot be cast to android.graphics.drawable.BitmapDrawable
    * 应该使用PNG等
    * */


    /*边框宽度*/
    private var mBorderWidth: Int = 0

    /*边框颜色*/
    private var mBorderColor: Int = Color.WHITE

    /*是否有阴影*/
    private var mHasShadow: Boolean = false

    /*阴影颜色*/
    private var mShadowColor: Int = Color.BLACK

    /*阴影模糊半径*/
    private var mShadowRadius: Float = 4.0f

    /*图片直径*/
    private var mBitmapDiameter: Float = 120f

    /*需要绘制的图片*/
    private lateinit var mBitmap: Bitmap

    /*图片的画笔*/
    private lateinit var mBitmapPaint: Paint
    /*边框的画笔*/
    private lateinit var mBorderPaint: Paint

    /*图片的渲染器*/
    private lateinit var mBitmapShader: BitmapShader

    /*控件宽度*/
    private var widthOrHeight: Float = 0f

    /*控件初始设置宽度*/
    private var widthSpecSize: Float = 0f

    /*控件初始设置高度*/
    private var heightSpecSize: Float = 0f

    constructor(context: Context) : super(context) {
        initData(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initData(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initData(context, attrs)
    }

    private fun initData(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            //如果用户有设置参数
            @SuppressLint("Recycle")
            var typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.DLRoundImageView)
            if (typedArray != null) {
                //读取边框宽度
                mBorderWidth =
                    typedArray.getInt(R.styleable.DLRoundImageView_borderWidth, mBorderWidth)
                //读取边框颜色
                mBorderColor =
                    typedArray.getColor(R.styleable.DLRoundImageView_borderColor, mBorderColor)
                //读取是否有阴影
                mHasShadow =
                    typedArray.getBoolean(R.styleable.DLRoundImageView_hasShadow, mHasShadow)
                //读取阴影颜色
                mShadowColor =
                    typedArray.getColor(R.styleable.DLRoundImageView_shadowColor, mShadowColor)
                //读取阴影模糊半径
                mShadowRadius =
                    typedArray.getFloat(R.styleable.DLRoundImageView_shadowRadius, mShadowRadius)
            }
        }
        //实例化图片的画笔
        mBitmapPaint = Paint()
        //设置打开抗锯齿功能
        mBitmapPaint.isAntiAlias = true
        //实例化边框的画笔
        mBorderPaint = Paint()
        //设置边框颜色
        mBorderPaint.color = mBorderColor
        //设置打开抗锯齿功能
        mBorderPaint.isAntiAlias = true
        //设置硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, mBorderPaint)
        //设置阴影参数
        if (mHasShadow) {
            mBorderPaint.setShadowLayer(mShadowRadius, 0f, 0f, mShadowColor)
        }

    }

    /**
     * 测量
     * MeasureSpec值由specMode和specSize共同组成
     * specMode的值有三个，MeasureSpec.EXACTLY、MeasureSpec.AT_MOST、MeasureSpec.UNSPECIFIED
     * MeasureSpec.EXACTLY：父视图希望子视图的大小应该是specSize中指定的。
     * MeasureSpec.AT_MOST：子视图的大小最多是specSize中指定的值，也就是说不建议子视图的大小超过specSize中给定的值。
     * MeasureSpec.UNSPECIFIED：我们可以随意指定视图的大小。
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 得到宽度数据模式
        // Get width data mode
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        // 得到宽度数据
        // Get width data
        widthSpecSize = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        // 得到高度数据模式
        // Get height data mode
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        // 得到高度数据
        // Get height data
        heightSpecSize = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        // 初始化控件高度为 图片直径 + 两个边框宽度（左右边框）
        widthOrHeight = mBitmapDiameter + mBorderWidth * 2
        // 判断宽高数据格式
        if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
            // 如果宽高都给定了数据
            if (widthSpecSize > heightSpecSize) {
                // 如果高度小，图片直径等于高度减两个边框宽度
                mBitmapDiameter = heightSpecSize - mBorderWidth * 2
                // 控件宽度为高度
                widthOrHeight = heightSpecSize
            } else {
                // 如果宽度小，图片直径等于宽度减两个边框宽度
                mBitmapDiameter = widthSpecSize - mBorderWidth * 2
                // 控件宽度为宽度
                widthOrHeight = widthSpecSize
            }
        } else if (widthSpecMode == MeasureSpec.EXACTLY) {
            // 如果只给了宽度，图片直径等于宽度减两个边框宽度
            mBitmapDiameter = widthSpecSize - mBorderWidth * 2
            // 控件宽度为宽度
            widthOrHeight = widthSpecSize
        } else if (heightSpecMode == MeasureSpec.EXACTLY) {
            // 如果只给了高度，图片直径等于高度减两个边框宽度
            mBitmapDiameter = heightSpecSize - mBorderWidth * 2
            // 控件宽度为高度
            widthOrHeight = heightSpecSize
        }
        // 保存测量好的宽高，向上取整再加2为了保证画布足够画下边框和阴影
        // Save the measured width and height
        setMeasuredDimension(
            ceil(widthOrHeight.toDouble() + 2).toInt(),
            ceil(widthOrHeight.toDouble() + 2).toInt()
        )
    }

    /**
     * 绘制
     * @param canvas
     */
    @SuppressLint("DrawAllocation", "CanvasSize")
    override fun onDraw(canvas: Canvas) {
        // 加载图片
        // load the bitmap
        loadBitmap()
        // 剪裁图片获得中间正方形
        // Crop the picture to get the middle square
        mBitmap = centerSquareScaleBitmap(
            mBitmap,
            Math.ceil(mBitmapDiameter.toDouble() + 1).toInt()
        )
        // 确保拿到图片
        if (mBitmap != null) {
            // 初始化渲染器
            // init shader
            mBitmapShader = BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            // 配置渲染器
            // Configuring the renderer
            mBitmapPaint.shader = mBitmapShader
            // 判断是否有阴影
            // Determine if there is a shadow
            if (mHasShadow) {
                // 配置阴影
                mBorderPaint.setShadowLayer(mShadowRadius, 0f, 0f, mShadowColor)
                // 画边框
                canvas.drawCircle(
                    widthOrHeight / 2, widthOrHeight / 2,
                    mBitmapDiameter / 2 + mBorderWidth - mShadowRadius, mBorderPaint
                )
                // 画图片
                canvas.drawCircle(
                    widthOrHeight / 2, widthOrHeight / 2,
                    mBitmapDiameter / 2 - mShadowRadius, mBitmapPaint
                )
            } else {
                // 配置阴影
                mBorderPaint.setShadowLayer(0f, 0f, 0f, mShadowColor)
                // 画边框
                canvas.drawCircle(
                    widthOrHeight / 2, widthOrHeight / 2,
                    mBitmapDiameter / 2 + mBorderWidth, mBorderPaint
                )
                // 画图片
                canvas.drawCircle(
                    widthOrHeight / 2, widthOrHeight / 2,
                    mBitmapDiameter / 2, mBitmapPaint
                )
            }
        }
    }

    /**
     * 加载图片
     * load the bitmap
     */
    private fun loadBitmap() {
        val bitmapDrawable = this.drawable as BitmapDrawable
        if (bitmapDrawable != null) mBitmap = bitmapDrawable.bitmap
    }

    /**
     * 裁切图片
     * Crop picture
     * 得到图片中间正方形的图片
     * Get a picture of the middle square of the picture
     * @param bitmap   原始图片
     * @param edgeLength  要裁切的正方形边长
     * @return Bitmap  图片中间正方形的图片
     */
    private fun centerSquareScaleBitmap(bitmap: Bitmap, edgeLength: Int): Bitmap {
        if (null == bitmap || edgeLength <= 0) {
            // 避免参数错误
            // Avoid parameter errors
            return bitmap
        }
        // 初始化结果
        // Initialization result
        var result = bitmap
        // 拿到图片原始宽度
        // Get the original width of the image
        val widthOrg = bitmap.width
        // 拿到图片原始高度
        // Get the original height of the image
        val heightOrg = bitmap.height
        // 要保证图片宽高要大于要裁切的正方形边长
        // Make sure that the width of the image is greater than the length of the square to be cropped.
        if (widthOrg >= edgeLength && heightOrg >= edgeLength) {
            // 得到对应宽高比例的另一个更长的边的长度
            // Get the length of the other longer side corresponding to the aspect ratio
            val longerEdge =
                (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(
                    widthOrg,
                    heightOrg
                ))
            // 分配宽度
            // Distribution width
            val scaledWidth = if (widthOrg > heightOrg) longerEdge else edgeLength
            // 分配高度
            // Distribution height
            val scaledHeight = if (widthOrg > heightOrg) edgeLength else longerEdge
            // 定义一个新压缩的图片位图
            // Define a new compressed picture bitmap
            val scaledBitmap: Bitmap
            scaledBitmap = try {
                // 压缩图片，以一个新的尺寸创建新的位图
                // Compress the image to create a new bitmap in a new size
                Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)
            } catch (e: Exception) {
                return bitmap
            }
            // 得到裁切中间位置图形的X轴偏移量
            // Get the X-axis offset of the cut intermediate position graphic
            val xTopLeft = (scaledWidth - edgeLength) / 2
            // 得到裁切中间位置图形的Y轴偏移量
            // Get the Y-axis offset of the cut intermediate position graphic
            val yTopLeft = (scaledHeight - edgeLength) / 2
            try {
                // 在指定偏移位置裁切出新的正方形位图
                // Crop a new square bitmap at the specified offset position
                result =
                    Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength)
                // 释放内存，回收资源
                // Free up memory, recycle resources
                scaledBitmap.recycle()
            } catch (e: Exception) {
                return bitmap
            }
        }
        return result
    }

    /**
     * 设置边框宽度
     * Set the border width
     * @param borderWidth
     */
    fun setBorderWidth(borderWidth: Int) {
        mBorderWidth = borderWidth
        // 重新绘制
        // repaint
        this.invalidate()
    }

    /**
     * 设置边框颜色
     * Set the border color
     * Exposure method
     * @param borderColor
     */
    fun setBorderColor(borderColor: Int) {
        if (mBorderPaint != null) mBorderPaint.color = borderColor
        // 重新绘制
        // repaint
        this.invalidate()
    }

    /**
     * 设置是否有阴影
     * Set whether there is a shadow
     * @param hasShadow
     */
    fun setHasShadow(hasShadow: Boolean) {
        mHasShadow = hasShadow
        // 重新绘制
        // repaint
        this.invalidate()
    }

    /**
     * 设置阴影颜色
     * Set the shadow color
     * @param shadowColor
     */
    fun setShadowColor(shadowColor: Int) {
        mShadowColor = shadowColor
        // 重新绘制
        // repaint
        this.invalidate()
    }

    /**
     * 设置阴影模糊半径
     * Set the shadow blur radius
     * @param shadowRadius
     */
    fun setShadowRadius(shadowRadius: Float) {
        mShadowRadius = shadowRadius
        // 重新绘制
        // repaint
        this.invalidate()
    }


}