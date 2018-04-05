package ui.anwesome.com.triangletosquareview

/**
 * Created by anweshmishra on 06/04/18.
 */

import android.app.Activity
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

    data class State (var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {
        fun update (stopcb : (Float) -> Unit) {
            scale += dir * 0.1f
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }

        fun startUpdating (startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * scale
                startcb()
            }
        }
    }

    data class Animator (var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if (animated) {
                try {
                    updatecb()
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex : Exception) {

                }
            }
        }

        fun start () {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class TriToSquareWave ( var i : Int, val state : State = State()) {

        fun draw(canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            canvas.save()
            canvas.translate(0f, h/2)
            val path : Path = Path()
            val n : Int = 10
            val x_gap : Float = (w)/10
            val w_x = (x_gap/2) * state.scale
            for (i in 0..n-1) {
                val y_h = (h/4) * (1 - 2 * (i%2))
                canvas.save()
                canvas.translate(x_gap * i, 0f)
                val path : Path = Path()
                path.moveTo(0f, 0f)
                path.lineTo(x_gap/2 - w_x, y_h)
                path.lineTo(x_gap/2 + w_x, y_h)
                path.lineTo(x_gap, 0f)
                canvas.drawPath(path, paint)
                canvas.restore()
            }
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer(var view : TriToSquareWaveView) {
        val animator : Animator = Animator(view)
        val wave : TriToSquareWave = TriToSquareWave(0)
        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            paint.color = Color.parseColor("#00C853")
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 6f
            paint.strokeCap = Paint.Cap.ROUND
            wave.draw(canvas, paint)
            animator.animate {
                wave.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            wave.startUpdating {
                animator.start()
            }
        }
    }

    companion object {
        fun create(activity : Activity) : TriToSquareWaveView {
            val view : TriToSquareWaveView = TriToSquareWaveView(activity)
            activity.setContentView(view)
            return view 
        }
    }
}