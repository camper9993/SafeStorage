package ru.danilov.safestorage.ui.login

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*
import ru.danilov.safestorage.R
import ru.danilov.safestorage.data.cryptography.Cryptography
import ru.danilov.safestorage.databinding.LoginFragmentBinding
import ru.danilov.safestorage.ui.SafeStorageActivity.Companion.key
import ru.danilov.safestorage.utils.navigate
import java.io.File
import java.io.FileWriter
import kotlin.properties.Delegates

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding : LoginFragmentBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var applicationRoot : String
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("app", AppCompatActivity.MODE_PRIVATE)
        applicationRoot = requireContext().applicationInfo.dataDir

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        binding.viewModel = viewModel
        val isNotFirstTime = File(applicationRoot).listFiles().contains(File("$applicationRoot/check.txt"))
        if (!isNotFirstTime) {
            binding.passphraseRepeatEditText.visibility = View.VISIBLE
            binding.passphraseRepeatEditText.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (binding.passphraseEditText.text.toString() == binding.passphraseRepeatEditText.text.toString() && binding.passphraseEditText.text.toString().isNotEmpty()) {
                        key = binding.passphraseEditText.text.toString()
                        viewModel.createStartFiles()
                        this.navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    else {
                        Toast.makeText(requireContext(), "Passwords aren't matching", Toast.LENGTH_LONG).show()
                    }
                    true
                } else {
                    false
                }
            }
        }
        else {
            binding.passphraseEditText.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.passphraseRepeatEditText.visibility = View.INVISIBLE
                    if (binding.passphraseEditText.text.toString().isNotEmpty()) {
                        key = binding.passphraseEditText.text.toString()
                        viewModel.setIsPasswordCorrect()
                        viewModel.isPasswordCorrect.observe(viewLifecycleOwner, Observer {
                            it?.let {
                                if (it && findNavController().currentDestination!!.id == R.id.loginFragment)
                                    this.navigate(R.id.action_loginFragment_to_homeFragment)
                                else
                                    Toast.makeText(
                                        requireContext(),
                                        "Wrong pass",
                                        Toast.LENGTH_LONG
                                    ).show()
                            }
                        })
                    }
                    Toast.makeText(requireContext(), "Wrong pass", Toast.LENGTH_LONG).show()

                    true
                } else {
                    false
                }
            }
        }
        return binding.root
    }
}