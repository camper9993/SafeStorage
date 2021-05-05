package ru.danilov.safestorage.domain.repository

import io.reactivex.Single
import java.io.File

interface CryptographyRepository {

    fun encryptFile(plainFile : File, path : String)

    fun decryptFile(encryptedFile : File)  : File

}