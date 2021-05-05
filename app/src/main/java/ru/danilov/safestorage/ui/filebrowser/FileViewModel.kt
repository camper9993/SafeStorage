package ru.danilov.safestorage.ui.filebrowser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.danilov.safestorage.models.PlainFile

class FileViewModel(val plainFile: PlainFile) : ViewModel() {
    val fileData = MutableLiveData<PlainFile>()

    init {
        fileData.value = plainFile
    }
}