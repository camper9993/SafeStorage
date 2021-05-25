package ru.danilov.safestorage.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.danilov.safestorage.R
import ru.danilov.safestorage.data.file_compression.ZipManager
import java.io.File

class DeleteFileDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.delete_confirmation))
            .setPositiveButton(getString(R.string.delete_ok)) { _,_ ->
                ZipManager.zip(File(arguments?.getString("path")), File(requireContext().applicationInfo.dataDir + "/bin/" + arguments?.getString("name")?.replace(".enc", ".zip")))
                File(arguments?.getString("path")).delete()
            }

            .setNegativeButton(getString(R.string.delete_no)) { _,_ ->

            }.show()

    companion object {
        const val TAG = "DeleteFileDialog"
    }
}