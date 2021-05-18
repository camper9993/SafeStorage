package ru.danilov.safestorage.data.file_browser

import android.os.Environment
import io.reactivex.Single
import org.apache.commons.io.FileUtils
import ru.danilov.safestorage.data.cryptography.EncryptionEngine
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.domain.repository.FileBrowserRepository
import java.io.File
import java.io.IOException


class FileBrowserRepositoryImpl : FileBrowserRepository {

    val ROOT = Environment.getExternalStorageDirectory().listFiles()

    override fun getFiles(path: String): Single<List<PlainFile>> {
        val listFile = mutableListOf<PlainFile>()

        File(path).listFiles().forEach {
            listFile.add(PlainFile(it.name, it.length() / 1024, it.absolutePath, it.isDirectory, it.name.endsWith(".enc")))
        }
        return Single.just(listFile)
    }
}