package com.mobcomp.vaultlock.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobcomp.vaultlock.database.Note
import com.mobcomp.vaultlock.database.NoteDatabaseDao
import kotlinx.android.synthetic.main.fragment_create_note.view.*
import kotlinx.coroutines.launch
import android.view.View
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CreateNoteViewModel(
    dataSource: NoteDatabaseDao,
    application: Application) : ViewModel() {

    val database = dataSource

    private val _navigateBackToMain = MutableLiveData<Boolean>()
    val navigateBackToMain: LiveData<Boolean>
            get() = _navigateBackToMain

    fun onNavigateToMain(){
        _navigateBackToMain.value = true
    }

    fun onNavigateBackToMain() {
        _navigateBackToMain.value = false
    }

    fun onCreateClicked(title : String, text : String){
        viewModelScope.launch {
            var note = Note()
            note.noteTitle = title
            note.note = text
            database.insert(note)
        }
        onNavigateToMain()
    }
}