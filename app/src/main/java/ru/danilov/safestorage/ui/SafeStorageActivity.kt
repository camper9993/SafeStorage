package ru.danilov.safestorage.ui

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Pair
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.danilov.safestorage.R
import ru.danilov.safestorage.data.cryptography.Cryptography

@AndroidEntryPoint
class SafeStorageActivity : AppCompatActivity() {

    companion object {
        lateinit var key : String
    }

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.root_layout)
        sharedPreferences = this.getSharedPreferences("app", MODE_PRIVATE)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
    }


    private fun storePassphrase(passphrase: String) {
        val newHash: Pair<String, String> = Cryptography.hash("1234", null)
        this.sharedPreferences
            .edit()
            .putString("passphrase", newHash.first)
            .putString("salt", newHash.second)
            .apply()
    }
}