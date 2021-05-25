package ru.danilov.safestorage.data.login

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import ru.danilov.safestorage.data.cryptography.EncryptionEngine
import ru.danilov.safestorage.domain.repository.LoginRepository
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(@ApplicationContext val applicationContext: Context) :  LoginRepository{

    private val applicationRoot: String = applicationContext.applicationInfo.dataDir

    override fun createStartFiles() {
        val rootDir = File("$applicationRoot/root")
        val tempDir = File("$applicationRoot/temp")
        val binDir = File("$applicationRoot/bin")
        rootDir.mkdir()
        tempDir.mkdir()
        binDir.mkdir()
        val testFile = File(applicationRoot, "check.txt")
        val writer = FileWriter(testFile)
        writer.append("Testing string for entrance.")
        writer.flush()
        writer.close()
        EncryptionEngine.encryptFile(
            "$applicationRoot/check.txt",
            "$applicationRoot/checkEncrypted.txt.enc"
        )
    }

    override fun checkPassword() : Single<Boolean> {
        val encryptedCheckFile = File("$applicationRoot/checkEncrypted.txt.enc")
        val plainCheckFile : File
        return try {
            plainCheckFile = File(EncryptionEngine.decryptFile(encryptedCheckFile).path)
            val reader = BufferedReader(FileReader(plainCheckFile))
            val text = reader.use {
                it.readText()
            }
            if (text == "Testing string for entrance.")
                Single.just(true)
            else
                Single.just(false)
        } catch (e : Exception) {
            e.printStackTrace()
            Single.just(false)
        }
    }
}