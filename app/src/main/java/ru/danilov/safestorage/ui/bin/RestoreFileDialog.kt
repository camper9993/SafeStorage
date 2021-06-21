package ru.danilov.safestorage.ui.bin

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.lingala.zip4j.ZipFile
import ru.danilov.safestorage.R
import ru.danilov.safestorage.data.zip.ZipManager
import java.io.File

@AndroidEntryPoint
class RestoreFileDialog : DialogFragment() {

    private val viewModel: BinViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.restore_confirmation))
            .setPositiveButton(getString(R.string.restore_ok)) { _,_ ->
                viewModel.unZipFile(requireContext().applicationInfo.dataDir + "/root/", ZipFile(arguments?.getString("path")))
                File(arguments?.getString("path")).delete()
            }

            .setNegativeButton(getString(R.string.restore_no)) { _,_ ->

            }.show()

    companion object {
        const val TAG = "DeleteFileDialog"
    }
}