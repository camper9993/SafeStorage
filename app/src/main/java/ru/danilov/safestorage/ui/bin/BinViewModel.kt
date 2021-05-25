package ru.danilov.safestorage.ui.bin

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.danilov.safestorage.domain.usecases.cryptography.DecryptFileUseCase
import ru.danilov.safestorage.domain.usecases.cryptography.EncryptFileUseCase
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.domain.usecases.filebrowser.GetFilesUseCase


class BinViewModel @ViewModelInject constructor(private val getFilesUseCase: GetFilesUseCase, private val decryptFileUseCase: DecryptFileUseCase) : ViewModel(){
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

    fun decryptFile(path: String) {
        decryptFileUseCase.setSourcePath(path)
        decryptFileUseCase.execute(
            onSuccess = {
                fileLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}