package ru.danilov.safestorage.data.zip

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