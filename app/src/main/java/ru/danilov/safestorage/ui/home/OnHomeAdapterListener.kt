package ru.danilov.safestorage.ui.home

import ru.danilov.safestorage.models.PlainFile

interface OnHomeAdapterListener {
    fun onClick(plainFile : PlainFile)
}