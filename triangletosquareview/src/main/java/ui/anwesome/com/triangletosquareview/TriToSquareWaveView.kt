package ui.anwesome.com.triangletosquareview

/**
 * Created by anweshmishra on 06/04/18.
 */

import android.content.Context
import android.view.View
import android.view.MotionEvent
import android.graphics.*

class TriToSquareWaveView (ctx : Context) : View(ctx) {

    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}