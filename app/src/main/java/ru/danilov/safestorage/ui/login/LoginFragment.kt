package ru.danilov.safestorage.ui.login

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import ru.danilov.safestorage.R
import ru.danilov.safestorage.databinding.LoginFragmentBinding
import ru.danilov.safestorage.ui.SafeStorageActivity.Companion.key
import ru.danilov.safestorage.utils.navigate
import java.io.File
import javax.inject.Inject
import javax.inject.Provider

class LoginFragment : Fragment() {

    private lateinit var binding : LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        val root_dir = File(requireContext().applicationInfo.dataDir + "/root")
        if (!root_dir.exists())
            root_dir.mkdir()
        binding.passphraseEditText.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                key = binding.passphraseEditText.text.toString()
                this.navigate(R.id.action_loginFragment_to_homeFragment)
                true
            } else {
                false
            }
        }
        return binding.root
    }


    
    
}