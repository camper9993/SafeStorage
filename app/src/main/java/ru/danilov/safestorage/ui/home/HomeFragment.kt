package ru.danilov.safestorage.ui.home

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.danilov.safestorage.R
import ru.danilov.safestorage.data.cryptography.EncryptionEngine
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.databinding.HomeFragmentBinding
import ru.danilov.safestorage.utils.navigate
import java.io.File
import java.lang.Exception

@AndroidEntryPoint
class HomeFragment : Fragment(), OnHomeAdapterListener {
    private lateinit var ROOT : String
    private lateinit var binding : HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter : HomeAdapter
    private lateinit var file : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ROOT = requireContext().applicationInfo.dataDir + "/root"
        adapter = HomeAdapter(this)
        viewModel.getFiles(ROOT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.viewModel = viewModel
        binding.filesRecyclerView.adapter = adapter

        viewModel.filesLiveData.observe(viewLifecycleOwner, {
            it?.let {
                initRecyclerView(it)
            }
        })
        binding.button.setOnClickListener {
            this.navigate(R.id.action_homeFragment_to_fileBrowserFragment)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFiles(ROOT)
    }

    private fun initRecyclerView(files: List<PlainFile>) {
        Log.i("FilesFragment", files.toString())
        adapter.addData(files)
    }

    override fun onClick(plainFile: PlainFile) {
        if (!plainFile.isDir) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val uri = EncryptionEngine.decryptFile(File(plainFile.path)).getUri(requireContext(), File(requireContext().applicationInfo.dataDir + "/temp/" + plainFile.name.substring(0, plainFile.name.length - 4)))
            intent.data = uri

            try {
                this.startActivityForResult(intent, 2020)
            } catch (e: ActivityNotFoundException) {
                print(e.stackTrace)
            }
        }
        else {
            viewModel.getFiles(plainFile.path)
        }
    }
}