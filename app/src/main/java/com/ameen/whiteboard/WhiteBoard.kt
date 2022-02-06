package com.ameen.whiteboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.ameen.whiteboard.model.LinePoint
import com.ameen.whiteboard.model.PaintSelectedType

class WhiteBoard(context: Context, attributeSet: AttributeSet? = null) :
    View(context, attributeSet) {

    private val TAG = "WhiteBoard"

    //Store the current Drawing type and Color.
    private var paintType: PaintSelectedType? = PaintSelectedType.HAND_DRAWING
    private val defaultPaintColor: Int = Color.BLACK

    //DrawPaint for drawing.
    private lateinit var drawPaintLine: Paint
    private lateinit var drawPaintArrow: Paint

    //Path to draw on.
    private var drawLinePath: MutableList<Path> = mutableListOf()

    //Line point for each painted line.
    private val startLine: MutableList<LinePoint> = mutableListOf()
    private val endLine: MutableList<LinePoint> = mutableListOf()
    private var startLinePoint: LinePoint = LinePoint()
    private var endLinePoint: LinePoint = LinePoint()

    init {
        setupPaint()
        setupNewDrawLinePaint(defaultPaintColor)
    }

    private fun setupPaint() {

        drawPaintLine = Paint().apply {
            color = defaultPaintColor
            isAntiAlias = true
            strokeWidth = 10F
            style = Paint.Style.STROKE
            pathEffect = null
        }

        drawPaintArrow = Paint().apply {
            color = defaultPaintColor
            isAntiAlias = true
            strokeWidth = 10F
            style = Paint.Style.FILL
            pathEffect = null
        }

    }

    override fun onDraw(canvas: Canvas?) {

        Log.i(TAG, "onDraw: ${drawPaintLine.color}")

        when (paintType) {

            PaintSelectedType.HAND_DRAWING ->
                for (path in drawLinePath) {
                    canvas?.drawPath(path, drawPaintLine)
                }

            PaintSelectedType.ARROW_DRAWING ->
                //Draw every single line.
                startLine.zip(endLine).forEach { currentLine ->

                    canvas?.drawLine(
                        currentLine.first.pointX,
                        currentLine.first.pointY,
                        currentLine.second.pointX,
                        currentLine.second.pointY,
                        drawPaintArrow
                    )

                }


            PaintSelectedType.RECTANGLE_DRAWING -> {}

            PaintSelectedType.CIRCLE_DRAWING -> {}

            else -> {}
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        Log.i(TAG, "onTouchEvent: ")

        val touchX = event?.x ?: 0F
        val touchY = event?.y ?: 0F

        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                //Get the coordinates for the start of the line.
                drawLinePath.last().moveTo(touchX, touchY)
                startLinePoint.pointX = touchX
                startLinePoint.pointY = touchY
                postInvalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                //Get the coordinates while moving finger on screen.
                drawLinePath.last().lineTo(touchX, touchY)
                endLinePoint.pointX = touchX
                endLinePoint.pointY = touchY
                postInvalidate()
            }

            MotionEvent.ACTION_UP -> {
                //Get the coordinates for the end of the line.
                startLine.add(LinePoint(startLinePoint.pointX, startLinePoint.pointY))
                endLine.add(LinePoint(endLinePoint.pointX, endLinePoint.pointY))
                postInvalidate()
            }
        }
        return true
    }

    /**
     * Change the type to be painted on screen.
     * @param drawType -> The type we will use.
     */
    fun setType(drawType: PaintSelectedType) {
        this.paintType = drawType
    }

    /**
     * Change the painting color from selected color
     * from the color pallet.
     * @param drawColor -> The color we will use.
     */
    fun setColor(drawColor: Int) {
        setupNewDrawLinePaint(drawColor)
        //drawPaintLine.color = paintColor!!
        //drawPaintArrow.color = paintColor!!
    }


    private fun setupNewDrawLinePaint(selectedColor: Int) {

        val lastIndex = drawLinePath.size

        Log.i(TAG, "setupNewDrawLinePaint: LastIndexWhileSetup --> $lastIndex")

        drawLinePath.add(lastIndex, Path())
        drawPaintLine.color = selectedColor

    }
}