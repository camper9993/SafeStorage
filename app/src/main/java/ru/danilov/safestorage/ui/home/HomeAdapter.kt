package ru.danilov.safestorage.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ru.danilov.safestorage.R
import ru.danilov.safestorage.databinding.EncryptedfileCardBinding
import ru.danilov.safestorage.models.PlainFile
import java.util.*

internal class HomeAdapter(val onHomeAdapterListener: OnHomeAdapterListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


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
        fun onBind(plainFile: PlainFile) {
            val encryptedfileCardBinding = dataBinding as EncryptedfileCardBinding
            val fileViewModel = HomeFileViewModel(plainFile)
            encryptedfileCardBinding.viewModel = fileViewModel

            itemView.setOnClickListener {
                onHomeAdapterListener.onFileClick(plainFile)
            }

        }
    }

}