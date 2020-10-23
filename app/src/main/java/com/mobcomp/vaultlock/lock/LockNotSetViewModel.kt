package com.mobcomp.vaultlock.lock

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobcomp.vaultlock.database.Password
import com.mobcomp.vaultlock.database.PasswordDatabaseDao
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.fragment_lock_not_set.view.*
import kotlinx.coroutines.launch

class LockNotSetViewModel(
    val database: PasswordDatabaseDao,
        application: Application
        ) : AndroidViewModel(application){

    private var _toastFailed = MutableLiveData<Boolean>()
    val toastFailed : LiveData<Boolean>
            get() = _toastFailed

    private var _onPasswordSet = MutableLiveData<Boolean>()
    val onPasswordSet : LiveData<Boolean>
        get() = _onPasswordSet

    fun onPasswordSuccess(){
        _onPasswordSet.value = true
    }

    fun onPasswordSucessDone(){
        _onPasswordSet.value = false
    }

    fun onToastFail(){
        _toastFailed.value = true
    }

    fun onToastReset(){
        _toastFailed.value = false
    }

    fun onSavePassword(fn : String, sn : String, tn : String, fon : String){
        if(fn.isEmpty() || sn.isEmpty() || tn.isEmpty() || fon.isEmpty()){
            onToastFail()
            return
        }
        var password = intArrayOf(fn.toInt(), sn.toInt(), tn.toInt(), fon.toInt())
        for(x in password){
            if(x > 12 || x < 1){
                onToastFail()
                return
            }
        }
        for(x in 0..2){
            if(password[x] == password[x+1]){
                onToastFail()
                return
            }
        }
        if(password[0] == 12){
            onToastFail()
            return
        }
        viewModelScope.launch {
            storePasswordInDatabase(password)
        }
    }

    private suspend fun storePasswordInDatabase(pass : IntArray){
        var password = Password()
        database.dropTable()
        password.password_f1 = pass[0].toString()
        password.password_f2 = pass[1].toString()
        password.password_f3 = pass[2].toString()
        password.password_f4 = pass[3].toString()
        database.insert(password)
        onPasswordSuccess()
    }
}