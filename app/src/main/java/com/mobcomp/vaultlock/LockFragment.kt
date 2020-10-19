package com.mobcomp.vaultlock

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mobcomp.vaultlock.databinding.FragmentLockBinding

import kotlin.math.roundToInt


class LockFragment : Fragment(), View.OnTouchListener {

    private lateinit var binding: FragmentLockBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentLockBinding>(
            inflater, R.layout.fragment_lock, container, false
        )
        binding.root.setOnTouchListener(this)
        return binding.root
    }

    fun updateRotation(x: Float, y: Float) {
        var r : Double = Math.atan2((x - binding.vaultLock.width / 2).toDouble(),
            (binding.vaultLock.height / 2 - y).toDouble()
        )
        var rotation : Float = Math.toDegrees(r).toFloat()

        var snapToPosition: Float = ((rotation / 30).roundToInt()) * 30f
        if (binding.vaultLock.rotation == snapToPosition) {
            return
        }
        binding.vaultLock.rotation = snapToPosition
        updateText(rotation)
        vibrate()
    }

    private fun updateText(rotation: Float) {
        var displayValue: Int = (rotation / 30).roundToInt()
        if (displayValue <= 0) {
            displayValue = displayValue + 12;
        }
        binding.value.text = displayValue.toString()
    }

    private fun vibrate() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(50)
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        var x : Float = event?.getX() ?: 0f
        var y : Float = event?.getY() ?: 0f

        if (event != null) {
            when(event.action){
                MotionEvent.ACTION_MOVE -> {
                    updateRotation(x,y)
                }
            }
        }
        return true
    }
}
