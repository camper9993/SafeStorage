package ru.danilov.safestorage.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.danilov.safestorage.domain.usecases.cryptography.DecryptFileUseCase
import ru.danilov.safestorage.domain.usecases.cryptography.EncryptFileUseCase
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.domain.usecases.filebrowser.GetFilesUseCase
import ru.danilov.safestorage.domain.usecases.zip.ZipUseCase
import java.io.File


class HomeViewModel @ViewModelInject constructor(private val zipUseCase: ZipUseCase, private val getFilesUseCase: GetFilesUseCase, private val decryptFileUseCase: DecryptFileUseCase) : ViewModel(){
    val filesLiveData = MutableLiveData<List<PlainFile>>()
    val fileLiveData = MutableLiveData<PlainFile>()
    val zipFileLiveData = MutableLiveData<File>()

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

    fun zipFile(files: File, zipFile : File) {
        zipUseCase.setFiles(files)
        zipUseCase.setZipFile(zipFile)
        zipUseCase.execute(
            onSuccess = {
                zipFileLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}