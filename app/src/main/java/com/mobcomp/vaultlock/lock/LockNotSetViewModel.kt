package com.mobcomp.vaultlock.lock

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.mobcomp.vaultlock.database.PasswordDatabaseDao

class LockNotSetViewModel(
    val database: PasswordDatabaseDao,
        application: Application
        ) : AndroidViewModel(application){

    fun onSavePassword(v: View){
        v.
    }

}