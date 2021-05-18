package ru.danilov.safestorage.ui.home

import ru.danilov.safestorage.models.PlainFile

interface OnHomeAdapterListener {
    fun onFileClick(plainFile : PlainFile)
}