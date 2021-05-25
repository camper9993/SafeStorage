package ru.danilov.safestorage.ui.bin

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import net.lingala.zip4j.ZipFile
import ru.danilov.safestorage.R
import ru.danilov.safestorage.data.file_compression.ZipManager
import ru.danilov.safestorage.utils.Constants
import java.io.Console
import java.io.File


class RestoreFileDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.restore_confirmation))
            .setPositiveButton(getString(R.string.restore_ok)) { _,_ ->
                ZipManager.unzip(requireContext().applicationInfo.dataDir + "/root/", ZipFile(arguments?.getString("path")))
                File(arguments?.getString("path")).delete()
            }

            .setNegativeButton(getString(R.string.restore_no)) { _,_ ->

            }.show()

    companion object {
        const val TAG = "DeleteFileDialog"
    }
}