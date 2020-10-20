package com.mobcomp.vaultlock.lock

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobcomp.vaultlock.database.PasswordDatabaseDao

class LockViewModel(
    val database: PasswordDatabaseDao,
    application: Application) : AndroidViewModel(application){

    //Does not do anything yet
    private fun passwordLogic(){
    }
}