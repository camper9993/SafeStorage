package ru.danilov.safestorage.domain.usecases.zip

import io.reactivex.Single
import net.lingala.zip4j.ZipFile
import ru.danilov.safestorage.data.zip.ZipRepositoryImpl
import ru.danilov.safestorage.domain.usecases.base.SingleUseCase
import java.io.File
import javax.inject.Inject

class ZipUseCase @Inject constructor(private val zipRepositoryImpl : ZipRepositoryImpl) : SingleUseCase<File>() {

    private var files: File? = null


    fun setFiles(files: File) {
        this.files = files
    }

    private var zipFile: File? = null


    fun setZipFile(zipFile: File) {
        this.zipFile = zipFile
    }

    override fun buildUseCaseSingle(): Single<File> = zipRepositoryImpl.zipFile(files!!, zipFile!!)

}