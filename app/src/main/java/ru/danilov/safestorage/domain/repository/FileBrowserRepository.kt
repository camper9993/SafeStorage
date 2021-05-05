package ru.danilov.safestorage.domain.repository

import io.reactivex.Single
import ru.danilov.safestorage.models.PlainFile

interface FileBrowserRepository {
    fun getFiles(path : String) : Single<List<PlainFile>>

    fun addFile(filePath: String, destinationPath: String) : Single<PlainFile>
}