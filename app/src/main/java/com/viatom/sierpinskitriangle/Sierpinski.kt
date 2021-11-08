package com.viatom.sierpinskitriangle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import java.lang.Thread.sleep
import java.util.*


class Sierpinski : SurfaceView, Runnable {
    private val wavePaint = Paint()
    private val bgPaint = Paint()



    fun clearScreen() {
        for (k in screenBuffer.indices) {
            screenBuffer[k] = false
        }
    }





    private var surfaceHolder: SurfaceHolder = this.holder

    private val screenBuffer = BooleanArray(320 * 320) {
        false
    }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }


    private fun init() {
        wavePaint.apply {
            color = getColor(R.color.wave_color)
            style = Paint.Style.FILL
        }

        bgPaint.apply {
            color = getColor(R.color.gray)
            style = Paint.Style.FILL

        }


    }




    private fun getColor(resource_id: Int): Int {
        return ContextCompat.getColor(context, resource_id)
    }



    var lX=-0.1f;
    var rX=1.2f;

    var uY=1.2f;
    var dY=-0.1f;

    fun transferX(d:Double):Float{
        return ((d-lX)/(rX-lX)*width).toFloat()
    }


    fun transferY(d:Double):Float{
        return (height-(d-dY)/(uY-dY)*height).toFloat()
    }


    fun transferX(d:Float):Float{
        return ((d-lX)/(rX-lX)*width).toFloat()
    }


    fun transferY(d:Float):Float{
        return (height-(d-dY)/(uY-dY)*height).toFloat()
    }

    var x1=0.6;
    var y1=0.7;

    override fun run() {
        while (true) {
            when((0..2).random()){
                0->{
                    x1 *= 0.5;
                    y1 *= 0.5;
                }
                1->{
                    x1=0.5*(0.5+x1);
                    y1=0.5*(1+y1);

                }
                2->{
                    x1=0.5*(1+x1);
                    y1 *= 0.5;
                }
            }


            val h = height.toFloat() / 320
            val w = width.toFloat() / 320

            val wux=(transferX(x1)/w).toInt()
            val wuy=(transferY(y1)/h).toInt()

            screenBuffer[wuy*320+wux]=true





            if (surfaceHolder.surface.isValid) {
                val canvas = surfaceHolder.lockCanvas()

                for (k in 0 until 320) {
                    for (j in 0 until 320) {
                        if (screenBuffer[j * 320 + k]) {
                            canvas.drawRect(k * w, j * h, k * w + w, j * h + h, bgPaint)
                        } else {
                            canvas.drawRect(k * w, j * h, k * w + w, j * h + h, wavePaint)
                        }
                    }
                }
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }


    fun resume() {
        val thread = Thread(this)
        thread.start()

    }




}