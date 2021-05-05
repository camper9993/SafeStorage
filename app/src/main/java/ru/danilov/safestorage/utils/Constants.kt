package ru.danilov.safestorage.utils

import java.security.SecureRandom

object Constants {
    private const val ITERATION_MULTIPLIER = 10
    private const val KEY_LENGTH = 256
    private const val SALT_LENGTH = KEY_LENGTH / 8
    private val RANDOM = SecureRandom()
}