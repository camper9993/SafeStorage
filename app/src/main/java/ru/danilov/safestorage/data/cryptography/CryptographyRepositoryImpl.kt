package ru.danilov.safestorage.data.cryptography

import ru.danilov.safestorage.domain.repository.CryptographyRepository
import java.io.File

class CryptographyRepositoryImpl() : CryptographyRepository{

    override fun encryptFile(plainFile : File, path : String) {
        EncryptionEngine.encryptFile(plainFile.toString(), path)
    }

    override fun decryptFile(encryptedFile: File) = EncryptionEngine.decryptFile(encryptedFile)

}