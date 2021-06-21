package ru.danilov.safestorage.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.danilov.safestorage.R
import ru.danilov.safestorage.data.zip.ZipManager
import ru.danilov.safestorage.ui.bin.BinViewModel
import java.io.File

@AndroidEntryPoint
class DeleteFileDialog : DialogFragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.delete_confirmation))
            .setPositiveButton(getString(R.string.delete_ok)) { _,_ ->
                viewModel.zipFile(File(arguments?.getString("path")), File(requireContext().applicationInfo.dataDir + "/bin/" + arguments?.getString("name")?.replace(".enc", ".zip")))
                File(arguments?.getString("path")).delete()
            }

            .setNegativeButton(getString(R.string.delete_no)) { _,_ ->

            }.show()

    companion object {
        const val TAG = "DeleteFileDialog"
    }
}