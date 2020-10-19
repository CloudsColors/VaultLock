package com.mobcomp.vaultlock

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.HapticFeedbackConstants
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

        when(event.action){
            MotionEvent.ACTION_MOVE -> {
                updateRotation(rotation)
            }
        }
        return super.onTouchEvent(event)
    }

    private fun updateRotation(rotation: Float) {
        var snapToPosition : Float = ((rotation / 30).roundToInt()) * 30f
        if(binding.vaultLock.rotation == snapToPosition){
            return
        }
        binding.vaultLock.rotation = snapToPosition
        updateText(rotation)
        vibrate()
    }

    private fun updateText(rotation: Float){
        var displayValue : Int = (rotation / 30).roundToInt()
        if(displayValue <= 0){
            displayValue = displayValue+12;
        }
        binding.value.text = displayValue.toString()
    }

    private fun vibrate(){
        if (Build.VERSION.SDK_INT >= 26) {
            (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))}
        else{
            (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(50)
        }
    }

}