package com.mobcomp.vaultlock.lock

import android.content.Context
import android.content.pm.ActivityInfo
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
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mobcomp.vaultlock.R
import com.mobcomp.vaultlock.database.PasswordDatabase
import com.mobcomp.vaultlock.databinding.FragmentLockBinding

import kotlin.math.roundToInt


class LockFragment : Fragment(), View.OnTouchListener {

    private lateinit var binding: FragmentLockBinding
    private var rightPosition: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.title = "VaultLock";

        binding = DataBindingUtil.inflate<FragmentLockBinding>(
            inflater, R.layout.fragment_lock, container, false
        )

        binding.root.setOnTouchListener(this)



        val application = requireNotNull(this.activity).application

        val dataSource = PasswordDatabase.getInstance(application).passwordDatabaseDao

        val viewModelFactory = LockViewModelFactory(dataSource, application)

        val lockViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(LockViewModel::class.java)

        lockViewModel.getPasswordFromDatabase()

        lockViewModel.isPasswordSet.observe(viewLifecycleOwner, Observer {
            if (!it){
                view?.findNavController()?.navigate(R.id.action_lockFragment_to_lockNotSetFragment)
                lockViewModel.navigateToSetPasswordDone()
            }
        })

        binding.lockViewModel = lockViewModel

        binding.setLifecycleOwner(this)

        binding.resetButton.setOnClickListener {
            resetButton()
        }

        binding.unlockButton.setOnClickListener {
            unlockButton()
        }

        lockViewModel.passwordText.observe(viewLifecycleOwner, Observer{
            binding.passwordText.text = it
        })

        return binding.root
    }

    private fun updateRotation(x: Float, y: Float) {
        var r : Double = Math.atan2((x - binding.vaultLock.width / 2).toDouble(),
            (binding.vaultLock.height / 2 - y).toDouble()
        )
        var rotation : Float = Math.toDegrees(r).toFloat()

        var snapToPosition: Float = ((rotation / 30).roundToInt()) * 30f
        if (binding.vaultLock.rotation == snapToPosition) {
            return
        }
        binding.vaultLock.rotation = snapToPosition
        binding.lockViewModel?.knobTurn((snapToPosition/30).roundToInt())
        vibrate()
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
                MotionEvent.ACTION_DOWN -> {
                    if(x in 700.00..800.00 ) {
                        rightPosition = true
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    updateRotation(x,y)
                    displayNumbers()

                    if(rightPosition){
                        updateRotation(x,y)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    rightPosition = false
                }
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        getActivity()?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    override fun onPause() {
        super.onPause()
        getActivity()?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR;
    }

    fun unlockButton(){
        var knobPos : Int = (binding.vaultLock.rotation / 30).roundToInt()
        Log.d("unlockButton","knobPos: $knobPos")
        var unlocked : Boolean? = binding.lockViewModel?.passwordLogic(knobPos)
        if(unlocked!!){
            Toast.makeText(context, "Access has been granted", Toast.LENGTH_LONG).show()
            view?.findNavController()?.navigate(R.id.action_lockFragment_to_menuFragment)
        }else{
            resetButton()
            Toast.makeText(context, "Wrong password", Toast.LENGTH_LONG).show()
        }
    }

    fun resetButton(){
        binding.lockViewModel?.resetPassword()
        binding.vaultLock.rotation = 0f
    }

    fun displayNumbers(){
        var lockNums = binding.lockNumbersCenter
        var knobPos : Int = (binding.vaultLock.rotation / 30).roundToInt()

        if (knobPos <= 0) {
            knobPos += 12;
        }


        var leftNum: Int
        var centerNum = knobPos
        var rightNum: Int

        if(knobPos == 12) {
            rightNum = knobPos - 11
        } else {
            rightNum = knobPos + 1
        }

        if(knobPos == 1){
            leftNum = knobPos + 11
        } else
            leftNum = knobPos - 1

        lockNums.text =
            getString(R.string.lockNumbersString, leftNum,centerNum,rightNum)

    }
}
