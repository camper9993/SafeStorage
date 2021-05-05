package ru.danilov.safestorage.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.danilov.safestorage.models.PlainFile

class HomeFileViewModel(val plainFile: PlainFile) : ViewModel() {
    val fileData = MutableLiveData<PlainFile>()

    init {
        fileData.value = plainFile
    }
}