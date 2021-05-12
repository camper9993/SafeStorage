package ru.danilov.safestorage.ui.filebrowser

import ru.danilov.safestorage.models.PlainFile

interface OnFileBrowserListener {
    fun onClick(plainFile : PlainFile)
}