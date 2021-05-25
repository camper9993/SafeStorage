package ru.danilov.safestorage.ui.bin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.danilov.safestorage.models.PlainFile

class BinFileViewModel(val plainFile: PlainFile) : ViewModel() {
    val fileData = MutableLiveData<PlainFile>()

    init {
        fileData.value = plainFile
    }
}