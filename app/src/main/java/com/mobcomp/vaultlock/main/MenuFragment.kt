package com.mobcomp.vaultlock.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mobcomp.vaultlock.R
import com.mobcomp.vaultlock.database.NoteDatabase
import com.mobcomp.vaultlock.databinding.FragmentMenuBinding
import androidx.lifecycle.Observer
import androidx.navigation.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMenuBinding>(inflater,
            R.layout.fragment_menu,container,false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        val viewModelFactory = ViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        val menuViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(MenuViewModel::class.java)

        binding.menuViewModel = menuViewModel

        val adapter = MenuAdapter()
        binding.noteList.adapter = adapter

        menuViewModel.notes.observe(viewLifecycleOwner, Observer{ it?.let{adapter.data = it}})

        menuViewModel.navigateToSearch.observe(viewLifecycleOwner, Observer {
            if(it) {
                view?.findNavController()?.navigate(R.id.action_menuFragment_to_createNoteFragment)
                menuViewModel.onNavigatedToSearch()
            }
        })
        return binding.root
    }




}