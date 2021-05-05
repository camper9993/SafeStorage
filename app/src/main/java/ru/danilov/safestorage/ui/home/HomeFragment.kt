package ru.danilov.safestorage.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.danilov.safestorage.R
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.databinding.HomeFragmentBinding
import ru.danilov.safestorage.utils.navigate

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var ROOT : String
    private lateinit var binding : HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter : HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ROOT = requireContext().applicationInfo.dataDir + "/root"
        adapter = HomeAdapter()
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

    private fun initRecyclerView(files: List<PlainFile>) {
        Log.i("FilesFragment", files.toString())
        adapter.addData(files)
    }
}