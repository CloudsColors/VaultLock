package com.mobcomp.vaultlock.lock

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobcomp.vaultlock.database.PasswordDatabaseDao
import kotlinx.android.synthetic.main.fragment_lock_not_set.view.*

class LockNotSetViewModel(
    val database: PasswordDatabaseDao,
        application: Application
        ) : AndroidViewModel(application){

    private var _toastFailed = MutableLiveData<Boolean>()
    val toastFailed : LiveData<Boolean>
            get() = _toastFailed

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
    }

}