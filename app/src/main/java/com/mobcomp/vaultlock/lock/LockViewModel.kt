package com.mobcomp.vaultlock.lock

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobcomp.vaultlock.database.PasswordDatabaseDao

class LockViewModel(
    val database: PasswordDatabaseDao,
    application: Application) : AndroidViewModel(application){

    private val pass: IntArray = intArrayOf(11,5,9,2)
    private var input: IntArray = intArrayOf(0,0,0,0)
    private var knobPos: Int = 12
    private var direction: Int = 0


    fun passwordLogic(userInput: Int, index: Int) : Boolean {
        input[index] = userInput

        if(input.contentEquals(pass)){
            return true
        }

        return false
    }

    fun knobTurn(knobPosition: Int){
        //Base
        if(direction == 0){
            knobPos = knobPosition
        }
        
        //Turn right


    }
}