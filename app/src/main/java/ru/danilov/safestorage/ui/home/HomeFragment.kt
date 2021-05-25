package ru.danilov.safestorage.ui.home

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
import com.geniusscansdk.scanflow.ScanConfiguration
import com.geniusscansdk.scanflow.ScanFlow
import dagger.hilt.android.AndroidEntryPoint
import ru.danilov.safestorage.BuildConfig
import ru.danilov.safestorage.R
import ru.danilov.safestorage.data.cryptography.EncryptionEngine
import ru.danilov.safestorage.databinding.HomeFragmentBinding
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.utils.Constants.REQUEST_IMAGE_CAPTURE
import ru.danilov.safestorage.utils.getUri
import ru.danilov.safestorage.utils.navigate
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(), OnHomeAdapterListener {
    private lateinit var ROOT : String
    private lateinit var binding : HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter : HomeAdapter
    lateinit var photoFile: File
    lateinit var fileToDelete : File

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
        binding.homeRecyclerView.adapter = adapter

        viewModel.filesLiveData.observe(viewLifecycleOwner, {
            it?.let {
                initRecyclerView(it)
            }
        })
        binding.button.setOnClickListener {
            showPopup(it)
        }
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
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
        if (!plainFile.isDir) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            fileToDelete = File(EncryptionEngine.decryptFile(File(plainFile.path)).path)
            val uri = fileToDelete.getUri(requireContext())
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

    override fun onLongFileClick(plainFile: PlainFile) {
        val bundle = Bundle()
        val deleteFileDialog = DeleteFileDialog()
        bundle.putString("path", plainFile.path)
        bundle.putString("name", plainFile.name)
        deleteFileDialog.arguments = bundle
        deleteFileDialog.show(parentFragmentManager, "DeleteFileDialogWithSetter")
        viewModel.getFiles(ROOT)
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.menu)
        popup.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.add_file -> {
                    this.navigate(R.id.action_homeFragment_to_fileBrowserFragment)
                }
                R.id.make_photo -> {
                    dispatchTakePictureIntent()
                }
                R.id.scan -> {
                    val scanConfiguration = ScanConfiguration().apply {
                        multiPage = true
                    }
                    ScanFlow.scanWithConfiguration(activity, scanConfiguration)
                }
            }

            true
        }

        popup.show()
    }

    private fun dispatchTakePictureIntent()  {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                photoFile = createImageFile()
                photoFile.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "ru.danilov.safestorage.provider.${BuildConfig.APPLICATION_ID}",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return File(
            ROOT + "/JPEG_${timeStamp}_.jpg"
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            val result = ScanFlow.getScanResultFromActivityResult(data).pdfFile
            EncryptionEngine.encryptFile(result!!.absolutePath, ROOT + "/" + result.name + ".enc")

        } catch (e: Exception) {
            e.printStackTrace()
        }
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