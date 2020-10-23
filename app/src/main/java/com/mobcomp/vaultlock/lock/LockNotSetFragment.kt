package com.mobcomp.vaultlock.lock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mobcomp.vaultlock.R
import com.mobcomp.vaultlock.database.PasswordDatabase
import com.mobcomp.vaultlock.databinding.FragmentLockNotSetBinding

class LockNotSetFragment : Fragment() {

    private lateinit var binding : FragmentLockNotSetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.title = "VaultLock - Set password combination";
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lock_not_set, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = PasswordDatabase.getInstance(application).passwordDatabaseDao

        val viewModelFactory = LockViewModelFactory(dataSource, application)

        val lockNotSetViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(LockNotSetViewModel::class.java)

        binding.viewModel = lockNotSetViewModel

        lockNotSetViewModel.toastFailed.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(context,"Incompatible password", Toast.LENGTH_LONG).show()
                lockNotSetViewModel.onToastReset()
            }
        })

        lockNotSetViewModel.onPasswordSet.observe(viewLifecycleOwner, Observer{
            if(it){
                view?.findNavController()?.navigate(R.id.action_lockNotSetFragment_to_menuFragment)
                lockNotSetViewModel.onPasswordSucessDone()
            }
        })

        binding.lifecycleOwner = this

        return binding.root
    }

}