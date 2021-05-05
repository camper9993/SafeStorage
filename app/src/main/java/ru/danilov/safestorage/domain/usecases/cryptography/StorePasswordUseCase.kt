package ru.danilov.safestorage.domain.usecases.cryptography

import io.reactivex.Single
import ru.danilov.safestorage.domain.repository.CryptographyRepository
import ru.danilov.safestorage.domain.usecases.base.SingleUseCase
import javax.inject.Inject

class StorePasswordUseCase @Inject constructor(private val cryptographyRepository: CryptographyRepository) {

    fun buildUseCase(password: String) {
//        cryptographyRepository.storePassword(password)
    }

}