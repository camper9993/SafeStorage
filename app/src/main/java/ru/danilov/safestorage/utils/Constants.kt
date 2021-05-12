package ru.danilov.safestorage.utils

import android.os.Environment
import java.security.SecureRandom

object Constants {
    val ROOT = Environment.getExternalStorageDirectory().absolutePath
    lateinit var PRIVATE_KEY : String
}