package ru.danilov.safestorage.domain.usecases.login

import io.reactivex.Single
import ru.danilov.safestorage.domain.repository.CryptographyRepository
import ru.danilov.safestorage.domain.repository.LoginRepository
import ru.danilov.safestorage.domain.usecases.base.SingleUseCase
import javax.inject.Inject


class CheckPasswordUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) : SingleUseCase<Boolean>() {

    override fun buildUseCaseSingle(): Single<Boolean> = loginRepository.checkPassword()


}