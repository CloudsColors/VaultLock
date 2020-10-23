package com.mobcomp.vaultlock.lock

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobcomp.vaultlock.database.Password
import com.mobcomp.vaultlock.database.PasswordDatabaseDao
import kotlinx.coroutines.launch
import java.util.*

class LockViewModel(
    val database: PasswordDatabaseDao,
    application: Application) : AndroidViewModel(application){

    private lateinit var pass: IntArray
    private var input: IntArray = intArrayOf(0,0,0,0)
    private var knobPosition: Int = 12
    private var direction: Int = 0
    private var passwordIndex = 0

    private var _isPasswordSet = MutableLiveData<Boolean>()
    val isPasswordSet : LiveData<Boolean>
        get() = _isPasswordSet

    private var _passwordText = MutableLiveData<String>()
    val passwordText : LiveData<String>
        get() = _passwordText

    fun navigateToSetPassword(){
        _isPasswordSet.value = false
    }

    fun navigateToSetPasswordDone(){
        _isPasswordSet.value = true
    }

    private fun updateText() {
        var tempPassText: String = ""
        if(passwordIndex == 0){
            _passwordText.value =  tempPassText
            return
        }
        for(i in 1..passwordIndex){
            tempPassText += "*"
        }
        _passwordText.value =  tempPassText
    }

    fun passwordLogic(userInput: Int) : Boolean {
        if(passwordIndex+1 > input.size){
            resetPassword()
            updateText()
            return false
        }
        input[passwordIndex] = userInput
        Log.d("PASSWORDCHECK", "Array: ${Arrays.toString(input)}")
        passwordIndex++
        updateText()
        if(input.contentEquals(pass)){
            Log.d("PASSWORDCHECK", "SUCCESS")
            return true
        }
        return false
    }

    fun resetPassword(){
        input = intArrayOf(0,0,0,0)
        passwordIndex = 0
        direction = 0
        updateText()
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

    fun getPasswordFromDatabase() {
        viewModelScope.launch{
            var password = IntArray(4)
            var query = database.getPassword()
            if(query.isNotEmpty()) {
                password[0] = query?.get(0).password_f1?.toInt()!!
                password[1] = query.get(0).password_f2.toInt()
                password[2] = query.get(0).password_f3.toInt()
                password[3] = query.get(0).password_f4.toInt()
                pass = password
            }else{
                navigateToSetPassword()
            }
        }
    }
}

