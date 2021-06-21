package ru.danilov.safestorage.ui.bin

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import ru.danilov.safestorage.R
import java.io.File

class FinalDeleteDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.final_delete))
            .setPositiveButton(getString(R.string.delete_ok)) { _, _ ->
                File(arguments?.getString("path")).delete()
            }

            .setNegativeButton(getString(R.string.delete_no)) { _, _ ->

            }.show()

    companion object {
        const val TAG = "DeleteFileDialog"
    }
}