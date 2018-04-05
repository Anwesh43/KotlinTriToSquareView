package ui.anwesome.com.kotlintritosquarewaveview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.triangletosquareview.TriToSquareWaveView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TriToSquareWaveView.create(this)
    }
}
