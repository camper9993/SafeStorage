package ru.danilov.safestorage.ui.bin

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.lingala.zip4j.ZipFile
import ru.danilov.safestorage.domain.usecases.cryptography.DecryptFileUseCase
import ru.danilov.safestorage.domain.usecases.cryptography.EncryptFileUseCase
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.domain.usecases.filebrowser.GetFilesUseCase
import ru.danilov.safestorage.domain.usecases.zip.UnzipUseCase
import ru.danilov.safestorage.domain.usecases.zip.ZipUseCase
import java.io.File


class BinViewModel @ViewModelInject constructor(private val unzipUseCase: UnzipUseCase, private val getFilesUseCase: GetFilesUseCase) : ViewModel(){

    val unzipFileLiveData = MutableLiveData<File>()
    val filesLiveData = MutableLiveData<List<PlainFile>>()

    fun unZipFile(path: String, zipFile: ZipFile) {
        unzipUseCase.setPath(path)
        unzipUseCase.setZipFile(zipFile)
        unzipUseCase.execute(
            onSuccess = {
                unzipFileLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

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