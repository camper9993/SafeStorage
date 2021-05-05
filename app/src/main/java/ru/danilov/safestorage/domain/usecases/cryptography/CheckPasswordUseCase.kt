package ru.danilov.safestorage.domain.usecases.cryptography

import io.reactivex.Single
import ru.danilov.safestorage.domain.repository.CryptographyRepository
import ru.danilov.safestorage.domain.usecases.base.SingleUseCase
import javax.inject.Inject


class CheckPasswordUseCase @Inject constructor(
    private val repository: CryptographyRepository
) : SingleUseCase<Boolean>() {

    private var password: String? = null


    fun setPassword(password: String) {
        this.password = password
    }


    override fun buildUseCaseSingle(): Single<Boolean> {
        TODO("Not yet implemented")
    }

}