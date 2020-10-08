package com.mobcomp.vaultlock

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MotionEvent
import com.mobcomp.vaultlock.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        var r : Double = Math.atan2((event!!.getX() - binding.vaultLock.width / 2).toDouble(),
            (binding.vaultLock.height / 2 - event.getY()).toDouble()
        )

        var rotation : Float = Math.toDegrees(r).toFloat()

        var displayValue : Int = (rotation / 30).roundToInt()
        if(displayValue <= 0){
            displayValue = displayValue+12;
        }
        binding.value.text = displayValue.toString()

        when(event?.action){
            MotionEvent.ACTION_MOVE -> {
                updateRotation(rotation)
            }
        }
        return super.onTouchEvent(event)
    }

    private fun updateRotation(rotation: Float) {
        binding.vaultLock.rotation = rotation
    }

}