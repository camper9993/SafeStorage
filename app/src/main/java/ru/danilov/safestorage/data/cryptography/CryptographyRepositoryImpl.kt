package ru.danilov.safestorage.data.cryptography

import io.reactivex.Single
import ru.danilov.safestorage.domain.repository.CryptographyRepository
import ru.danilov.safestorage.models.PlainFile
import java.io.File

class CryptographyRepositoryImpl : CryptographyRepository{

    override fun encryptFile(plainFilePath: String, encryptedFilePath: String) : Single<PlainFile> = Single.just(EncryptionEngine.encryptFile(plainFilePath, encryptedFilePath))

    override fun decryptFile(encryptedFile: File) : Single<PlainFile> = Single.just(EncryptionEngine.decryptFile(encryptedFile))

}