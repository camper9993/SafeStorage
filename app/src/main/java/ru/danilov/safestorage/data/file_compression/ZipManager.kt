package ru.danilov.safestorage.data.file_compression

import net.lingala.zip4j.ZipFile
import java.io.*

object ZipManager {

    fun zip(files: File, zipFile: File) {
        ZipFile(zipFile).addFile(files)
    }


    fun unzip(path: String, zipFile: ZipFile) {
        zipFile.extractAll(path)
    }
}