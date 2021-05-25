package ru.danilov.safestorage

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.geniusscansdk.core.GeniusScanSDK
import com.geniusscansdk.core.LicenseException
import dagger.hilt.android.HiltAndroidApp
import ru.danilov.safestorage.utils.Constants

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        try {
            GeniusScanSDK.init(applicationContext, Constants.SCAN_API_KEY)
        }
        catch (e: LicenseException) {
            e.printStackTrace()
        }
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}