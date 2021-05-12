package ru.danilov.safestorage.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.danilov.safestorage.data.cryptography.CryptographyRepositoryImpl
import ru.danilov.safestorage.data.file_browser.FileBrowserRepositoryImpl
import ru.danilov.safestorage.data.login.LoginRepositoryImpl
import ru.danilov.safestorage.domain.repository.CryptographyRepository
import ru.danilov.safestorage.domain.repository.FileBrowserRepository
import ru.danilov.safestorage.domain.repository.LoginRepository
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideCryptographyRepository(
    ): CryptographyRepository {
        return CryptographyRepositoryImpl(
        )
    }

    @Singleton
    @Provides
    fun provideFileBrowserRepository(): FileBrowserRepository {
        return FileBrowserRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideLoginRepository(@ApplicationContext applicationContext: Context): LoginRepository {
        return LoginRepositoryImpl(applicationContext)
    }

}