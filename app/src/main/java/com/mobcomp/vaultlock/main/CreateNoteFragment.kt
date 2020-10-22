package com.mobcomp.vaultlock.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        val viewModelFactory = ViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        val createNoteViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CreateNoteViewModel::class.java)

        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_create_note, container, false)

        binding.createNoteViewModel = createNoteViewModel

        createNoteViewModel.navigateBackToMain.observe(viewLifecycleOwner, Observer{
            if(it){
                view?.findNavController()?.navigate(R.id.action_createNoteFragment_to_menuFragment3)
                createNoteViewModel.onNavigateBackToMain()
            }
        })

        binding.clearButton.setOnClickListener {
            binding.textNote.text.clear()
            binding.titleText.text.clear()
        }
        return binding.root
    }
}