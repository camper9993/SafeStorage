package ru.danilov.safestorage.models

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream

data class PlainFile(
    val name: String,
    val size: Long,
    val path: String,
    val isDir: Boolean,
    val isEncrypted: Boolean
) {
    fun getUri(context: Context, file : File): Uri? {
        val authority = "ru.danilov.safestorage.provider." + context.packageName
        return FileProvider.getUriForFile(context, authority, file)
    }
}