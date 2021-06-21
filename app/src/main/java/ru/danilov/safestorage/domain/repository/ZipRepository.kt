package ru.danilov.safestorage.domain.repository

import io.reactivex.Single
import net.lingala.zip4j.ZipFile
import java.io.File

interface ZipRepository {

    fun zipFile(files: File, zipFile: File) : Single<File>

    fun unzipFile(path: String, zipFile: ZipFile) : Single<File>
}