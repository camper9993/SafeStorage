package ru.danilov.safestorage.ui.bin

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.danilov.safestorage.BuildConfig
import ru.danilov.safestorage.R
import ru.danilov.safestorage.data.cryptography.EncryptionEngine
import ru.danilov.safestorage.databinding.BinFragmentBinding
import ru.danilov.safestorage.databinding.HomeFragmentBinding
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.utils.Constants.REQUEST_IMAGE_CAPTURE
import ru.danilov.safestorage.utils.getUri
import ru.danilov.safestorage.utils.navigate
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class BinFragment : Fragment(), OnBinAdapterListener {
    private lateinit var ROOT : String
    private lateinit var binding : BinFragmentBinding
    private val viewModel: BinViewModel by viewModels()
    private lateinit var adapter : BinAdapter
    lateinit var photoFile: File
    lateinit var fileToDelete : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ROOT = requireContext().applicationInfo.dataDir + "/bin"
        adapter = BinAdapter(this)
        viewModel.getFiles(ROOT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bin_fragment, container, false)
        binding.viewModel = viewModel
        binding.binRecyclerView.adapter = adapter
        viewModel.filesLiveData.observe(viewLifecycleOwner, {
            it?.let {
                initRecyclerView(it)
            }
        })
        binding.bottomNavigationBin.menu.findItem(R.id.main_page).isChecked = false
        binding.bottomNavigationBin.menu.findItem(R.id.bucket).isChecked = true
        binding.bottomNavigationBin.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_page -> {
                    if (findNavController().currentDestination!!.id != R.id.homeFragment)
                        this.navigate(R.id.action_binFragment_to_homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bucket -> {
                    if (findNavController().currentDestination!!.id != R.id.binFragment)
                        this.navigate(R.id.action_homeFragment_to_binFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
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

    override fun onFileClick(plainFile: PlainFile) {
        val bundle = Bundle()
        val deleteFileDialog = RestoreFileDialog()
        bundle.putString("path", plainFile.path)
        bundle.putString("name", plainFile.name)
        deleteFileDialog.arguments = bundle
        deleteFileDialog.show(parentFragmentManager, "DeleteFileDialogWithSetter")
        viewModel.getFiles(ROOT)
    }

    override fun onLongFileClick(plainFile: PlainFile) {
        val bundle = Bundle()
        val deleteFileDialog = FinalDeleteDialog()
        bundle.putString("path", plainFile.path)
        deleteFileDialog.arguments = bundle
        deleteFileDialog.show(parentFragmentManager, "DeleteFileDialogWithSetter")
        viewModel.getFiles(ROOT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2020)
        try {
            if (fileToDelete.exists()) {
                fileToDelete.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            EncryptionEngine.encryptFile(photoFile.path, photoFile.path + ".enc")
            photoFile.delete()
        }
    }
}