package ru.danilov.safestorage.ui.filebrowser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ru.danilov.safestorage.R
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.databinding.FileCardBinding
import java.util.ArrayList

internal class FileBrowserAdapter(val viewModel: FileBrowserViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val plainFiles: MutableList<PlainFile> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val fileCardBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.file_card, parent, false
        )
        return FileBrowserViewHolder(fileCardBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FileBrowserViewHolder).onBind(getItem(position))
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

    inner class FileBrowserViewHolder(private val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root) {
        fun onBind(plainFile: PlainFile) {
            val fileCardBinding = dataBinding as FileCardBinding
            val fileViewModel = FileViewModel(plainFile)
            fileCardBinding.fileViewModel = fileViewModel

            itemView.setOnClickListener {
                if (!plainFile.isDir) {
                    viewModel.addFile(
                        plainFile.path,
                        it.context.applicationInfo.dataDir + "/root/" + plainFile.name + ".enc"
                    )
                }
                else {
                    viewModel.getFiles(plainFile.path)
                }
            }

        }
    }

}