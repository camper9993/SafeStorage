package ru.danilov.safestorage.utils

import android.os.Environment
import java.security.SecureRandom

object Constants {
    val ROOT = Environment.getExternalStorageDirectory().absolutePath
    val REQUEST_IMAGE_CAPTURE = 14
    val SCAN_API_KEY = "533c500656520408045402593943404d00025c0d5859441711575203444c5f420353013d09030403510a070a5156"
}