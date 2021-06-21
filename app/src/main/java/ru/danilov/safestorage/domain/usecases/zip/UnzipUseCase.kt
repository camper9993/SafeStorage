package ru.danilov.safestorage.domain.usecases.zip

import io.reactivex.Single
import net.lingala.zip4j.ZipFile
import ru.danilov.safestorage.data.zip.ZipRepositoryImpl
import ru.danilov.safestorage.domain.usecases.base.SingleUseCase
import java.io.File
import javax.inject.Inject

class UnzipUseCase @Inject constructor(private val zipRepositoryImpl : ZipRepositoryImpl) : SingleUseCase<File>() {

    private var path: String? = null


    fun setPath(path: String) {
        this.path = path
    }

    private var zipFile: ZipFile? = null


    fun setZipFile(zipFile: ZipFile) {
        this.zipFile = zipFile
    }

    override fun buildUseCaseSingle(): Single<File> = zipRepositoryImpl.unzipFile(path!!, zipFile!!)

}