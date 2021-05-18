package ru.danilov.safestorage.utils

import android.os.Environment
import java.security.SecureRandom

object Constants {
    val ROOT = Environment.getExternalStorageDirectory().absolutePath
    val REQUEST_IMAGE_CAPTURE = 14
}