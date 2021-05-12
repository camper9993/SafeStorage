package ru.danilov.safestorage.ui.filebrowser

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.domain.usecases.cryptography.EncryptFileUseCase
import ru.danilov.safestorage.domain.usecases.filebrowser.GetFilesUseCase

class FileBrowserViewModel @ViewModelInject constructor(private val getFilesUseCase: GetFilesUseCase, private val encryptFileUseCase: EncryptFileUseCase) : ViewModel() {
    val filesLiveData = MutableLiveData<List<PlainFile>>()

    val fileLiveData = MutableLiveData<PlainFile>()

    fun setFiles(path: String) {
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

    fun encryptFile(sourcePath: String, destinationPath : String) {
        encryptFileUseCase.setDestinationPath(destinationPath)
        encryptFileUseCase.setSourcePath(sourcePath)
        encryptFileUseCase.execute(
            onSuccess = {
                fileLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }


}