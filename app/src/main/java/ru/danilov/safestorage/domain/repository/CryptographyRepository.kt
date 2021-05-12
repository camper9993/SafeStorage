package ru.danilov.safestorage.domain.repository

import io.reactivex.Single
import ru.danilov.safestorage.models.PlainFile
import java.io.File

interface CryptographyRepository {

    fun encryptFile(plainFilePath: String, encryptedFilePath: String) : Single<PlainFile>

    fun decryptFile(encryptedFile : File)  : Single<PlainFile>

}