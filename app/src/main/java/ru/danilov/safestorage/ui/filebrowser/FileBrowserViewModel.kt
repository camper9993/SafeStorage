package ru.danilov.safestorage.ui.filebrowser

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.domain.usecases.filebrowser.AddFileUseCase
import ru.danilov.safestorage.domain.usecases.filebrowser.GetFilesUseCase

class FileBrowserViewModel @ViewModelInject constructor(private val getFilesUseCase: GetFilesUseCase, private val addFileUseCase: AddFileUseCase) : ViewModel() {
    val filesLiveData = MutableLiveData<List<PlainFile>>()

    val fileLiveData = MutableLiveData<PlainFile>()

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

    fun addFile(sourcePath: String, destinationPath : String) {
        addFileUseCase.setDestinationPath(destinationPath)
        addFileUseCase.setSourcePath(sourcePath)
        addFileUseCase.execute(
            onSuccess = {
                fileLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }


}