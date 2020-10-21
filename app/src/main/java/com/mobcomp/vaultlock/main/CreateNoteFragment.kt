package com.mobcomp.vaultlock.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.mobcomp.vaultlock.R
import com.mobcomp.vaultlock.database.Note
import com.mobcomp.vaultlock.database.NoteDatabase
import com.mobcomp.vaultlock.database.NoteDatabaseDao
import com.mobcomp.vaultlock.databinding.FragmentCreateNoteBinding

class CreateNoteFragment : Fragment() {

    private lateinit var binding: FragmentCreateNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        binding =  DataBindingUtil.inflate<FragmentCreateNoteBinding>(inflater, R.layout.fragment_create_note, container, false)
        binding.saveButton.setOnClickListener {
            val note = Note()
            note.noteTitle = binding.titleText.toString()
            note.note = binding.textNote.toString()
            view?.findNavController()?.navigate(R.id.action_createNoteFragment_to_menuFragment3)
        }
        binding.clearButton.setOnClickListener {
            binding.textNote.text.clear()
            binding.titleText.text.clear()
        }
        return binding.root
    }

    private suspend fun insert(note: Note, dataSource: NoteDatabaseDao){
        dataSource.insert(note)
    }
}