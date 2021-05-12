package ru.danilov.safestorage.domain.repository

import io.reactivex.Single

interface LoginRepository {

    fun createStartFiles()

    fun checkPassword() : Single<Boolean>

}