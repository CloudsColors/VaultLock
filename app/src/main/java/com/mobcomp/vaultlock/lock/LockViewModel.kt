package com.mobcomp.vaultlock.lock

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.mobcomp.vaultlock.database.PasswordDatabaseDao

class LockViewModel(
    val database: PasswordDatabaseDao,
    application: Application) : AndroidViewModel(application){

    private val pass: IntArray = intArrayOf(11,5,9,2)
    private var input: IntArray = intArrayOf(0,0,0,0)
    private var knobPosition: Int = 12
    private var direction: Int = 0
    private var passwordIndex = 0


    private fun passwordLogic(userInput: Int) : Boolean {
        input[passwordIndex] = userInput
        passwordIndex++
        if(input.contentEquals(pass)){
            Log.d("PASSWORDCHECK", "SUCCESS")
            return true
        }
        return false
    }

    fun resetPassword(){
        input = intArrayOf(0,0,0,0)
        passwordIndex = 0
    }

    fun knobTurn(knobPos: Int){
        var oldDirection = direction
        var knobPosCorrected = knobPos

        if (knobPosCorrected <= 0) {
            knobPosCorrected = knobPosCorrected + 12;
        }
        if(knobPosCorrected == knobPosition){
            return
        }
        //Base
        if(direction == 0){
            if (knobPosCorrected == 1){
                direction = 1
                oldDirection = 1
            }else{
                direction = -1
                oldDirection = -1
            }
        }else{
            direction = knobPosCorrected - knobPosition
        }
        if(direction < -1){
            direction = 1
        }
        if(direction > 1){
            direction = -1
        }
        if(oldDirection != direction){
            Log.d("KNOBPASS", "ENTRYCODE $knobPosition")
            passwordLogic(knobPosition)
        }
        knobPosition = knobPosCorrected
        Log.d("KNOBDIRECTION", "direction: $direction knobPosition: $knobPosition knobPosCorrected: $knobPosCorrected")
    }
}