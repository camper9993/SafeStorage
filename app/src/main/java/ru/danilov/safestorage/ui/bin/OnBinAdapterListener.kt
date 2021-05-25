package ru.danilov.safestorage.ui.bin

import ru.danilov.safestorage.models.PlainFile

interface OnBinAdapterListener {
    fun onFileClick(plainFile : PlainFile)

    fun onLongFileClick(plainFile: PlainFile)
}