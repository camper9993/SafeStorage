package ru.danilov.safestorage.data.cryptography;

import android.util.Log
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.ui.SafeStorageActivity.Companion.key
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import javax.crypto.CipherOutputStream

object EncryptionEngine {

    fun encryptFile(plainFilePath: String, encryptedFilePath: String) : PlainFile {
        val encryptedFile = File(encryptedFilePath)
        val plainFile = File(plainFilePath)
        val buffer = ByteArray(1024 * 1024)
        try {
            var inputStream : InputStream? = null
            var outputStream : OutputStream? = null

            val cipherPair = Cryptography.newCipher(key)
            val cipher = cipherPair.first
            val header = cipherPair.second
            try {
                inputStream = plainFile.inputStream()
                outputStream = encryptedFile.outputStream()

                outputStream.write(header)
                outputStream = CipherOutputStream(outputStream, cipher)

                var bytesRead = inputStream.read(buffer)
                val time = System.currentTimeMillis()
                Log.d("Start:", time.toString())
                while (bytesRead != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    bytesRead = inputStream.read(buffer)
                }
                Log.d("End:", (System.currentTimeMillis() - time).toString())
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: Exception) {
            encryptedFile.delete()
            throw e
        }
        encryptedFile.createNewFile()
        return PlainFile(encryptedFile.name, encryptedFile.length() / 1024, encryptedFile.absolutePath,
            isDir = false,
            isEncrypted = true
        )
    }

    fun decryptFile(encryptedFile: File) : PlainFile {
        val plainFile = File(encryptedFile.absolutePath.substring(0, encryptedFile.absolutePath.length - 4).replace("root".toRegex(), "temp"))
        val buffer = ByteArray(1024 * 1024)
        try {
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                inputStream = encryptedFile.inputStream()
                val cipher = Cryptography.getCipher(key, inputStream)
                outputStream = CipherOutputStream(plainFile.outputStream(), cipher)
                var bytesRead = inputStream.read(buffer)
                val time = System.currentTimeMillis()
                Log.d("Start:", time.toString())
                while (bytesRead != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    bytesRead = inputStream.read(buffer)
                }
                Log.d("End:", (System.currentTimeMillis() - time).toString())
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: Exception) {
            plainFile.delete()
            throw RuntimeException(e)
        }
        return PlainFile(plainFile.name, plainFile.length() / 1024, plainFile.absolutePath,
            isDir = false,
            isEncrypted = false
        )
    }

}