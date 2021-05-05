package ru.danilov.safestorage.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.domain.usecases.filebrowser.GetFilesUseCase


class HomeViewModel @ViewModelInject constructor(private val getFilesUseCase: GetFilesUseCase) : ViewModel(){
    val filesLiveData = MutableLiveData<List<PlainFile>>()

    fun getFiles(path: String) {
        getFilesUseCase.setPath(path)
        getFilesUseCase.execute(
            onSuccess = {
                filesLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}