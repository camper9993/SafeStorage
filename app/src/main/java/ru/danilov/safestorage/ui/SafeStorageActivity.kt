package ru.danilov.safestorage.ui

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.danilov.safestorage.R
import java.math.BigInteger
import java.security.SecureRandom

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
        if (sharedPreferences.getString("salt", null) == null) {
            storeSalt()
        }
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


    private fun storeSalt() {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)
        this.sharedPreferences
            .edit()
            .putString("salt", toHex(salt))
            .apply()
    }

    private fun toHex(values: ByteArray): String? {
        return String.format("%0" + values.size * 2 + "X", BigInteger(1, values))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageData: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageData)
        for (fragment in supportFragmentManager.primaryNavigationFragment!!.childFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, imageData)
        }
    }
}