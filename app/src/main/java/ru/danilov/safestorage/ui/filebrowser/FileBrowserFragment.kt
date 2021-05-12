package ru.danilov.safestorage.ui.filebrowser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ru.danilov.safestorage.R
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.databinding.FilebrowserFragmentBinding
import ru.danilov.safestorage.utils.Constants.ROOT

@AndroidEntryPoint
class FileBrowserFragment : Fragment(), OnFileBrowserListener{

    private lateinit var binding : FilebrowserFragmentBinding
    private val viewModel: FileBrowserViewModel by viewModels()
    private lateinit var adapter : FileBrowserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FileBrowserAdapter(this)
        viewModel.setFiles(ROOT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.filebrowser_fragment, container, false)
        binding.viewModel = viewModel
        binding.filesRecyclerView.adapter = adapter

        viewModel.filesLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                initRecyclerView(it)
            }
        })
        return binding.root
    }

    private fun initRecyclerView(files: List<PlainFile>) {
        Log.i("FilesFragment", files.toString())
        adapter.addData(files)
    }

    override fun onClick(plainFile: PlainFile) {
        if (!plainFile.isDir) {
            viewModel.encryptFile(
                plainFile.path,
                requireContext().applicationInfo.dataDir + "/root/" + plainFile.name + ".enc"
            )
        }
        else {
            viewModel.setFiles(plainFile.path)
        }
    }
}