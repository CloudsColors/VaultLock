package com.mobcomp.vaultlock.main

import android.app.Application
import androidx.lifecycle.*
import com.mobcomp.vaultlock.database.NoteDatabaseDao
import kotlinx.coroutines.launch

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
}