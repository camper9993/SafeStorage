package ru.danilov.safestorage.ui.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ru.danilov.safestorage.R
import ru.danilov.safestorage.data.cryptography.Cryptography
import ru.danilov.safestorage.data.cryptography.EncryptionEngine
import ru.danilov.safestorage.databinding.EncryptedfileCardBinding
import ru.danilov.safestorage.models.PlainFile
import java.io.File
import java.util.*

internal class HomeAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val plainFiles: MutableList<PlainFile> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val fileCardBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.encryptedfile_card, parent, false
        )
        return HomeViewHolder(fileCardBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeViewHolder).onBind(getItem(position))
    }

    override fun getItemCount(): Int {
        return plainFiles.size
    }

    private fun getItem(position: Int): PlainFile {
        return plainFiles[position]
    }

    fun addData(list: List<PlainFile>) {
        this.plainFiles.clear()
        this.plainFiles.addAll(list)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
        dataBinding.root
    ) {
        fun onBind(album: PlainFile) {
            val encryptedfileCardBinding = dataBinding as EncryptedfileCardBinding
            val fileViewModel = HomeFileViewModel(album)
            encryptedfileCardBinding.viewModel =  fileViewModel

            itemView.setOnClickListener {
                if (!album.isDir) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                    intent.data =
                        album.getUri(it.context, EncryptionEngine.decryptFile(File(album.path)))

                    try {
                        startActivity(it.context, intent, null)
                    } catch (e: ActivityNotFoundException) {
                        print(e.stackTrace)
                    }
                }
            }

        }
    }

}