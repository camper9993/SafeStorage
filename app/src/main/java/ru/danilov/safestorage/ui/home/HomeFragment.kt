package ru.danilov.safestorage.ui.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.danilov.safestorage.R
import ru.danilov.safestorage.data.cryptography.EncryptionEngine
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.databinding.HomeFragmentBinding
import ru.danilov.safestorage.utils.Constants.REQUEST_IMAGE_CAPTURE
import ru.danilov.safestorage.utils.navigate
import java.io.File

@AndroidEntryPoint
class HomeFragment : Fragment(), OnHomeAdapterListener {
    private lateinit var ROOT : String
    private lateinit var binding : HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter : HomeAdapter

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
            showPopup(it)
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

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.add_file -> {
                    this.navigate(R.id.action_homeFragment_to_fileBrowserFragment)
                }
                R.id.make_photo -> {
                    dispatchTakePictureIntent()
                }
            }

            true
        })

        popup.show()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}