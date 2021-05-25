package ru.danilov.safestorage.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import java.io.File
import java.net.URI

fun Fragment.navigate(resId: Int, bundle: Bundle? = null) {
    NavHostFragment.findNavController(this).navigate(resId, bundle)
}

fun Activity.findNavController(@IdRes viewId: Int): NavController =
    Navigation.findNavController(this, viewId)

fun File.getUri(context : Context) : Uri {
    val authority = "ru.danilov.safestorage.provider." + context.packageName
    return FileProvider.getUriForFile(context, authority, this)
}