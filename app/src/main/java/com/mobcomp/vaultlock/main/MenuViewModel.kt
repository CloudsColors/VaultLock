package com.mobcomp.vaultlock.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobcomp.vaultlock.database.NoteDatabaseDao

class MenuViewModel(
    dataSource: NoteDatabaseDao,
    application: Application) : ViewModel() {

    val database = dataSource
    val notes = database.getAll()

    private val _navigateToSearch = MutableLiveData<Boolean>()
    val navigateToSearch: LiveData<Boolean>
        get() = _navigateToSearch

    fun onFabClicked() {
        _navigateToSearch.value = true
    }

    fun onNavigatedToSearch() {
        _navigateToSearch.value = false
    }

    private suspend fun getAll(){
        database.getAll()
    }


}