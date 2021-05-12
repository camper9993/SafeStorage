package ru.danilov.safestorage.domain.usecases.login

import io.reactivex.Single
import ru.danilov.safestorage.domain.repository.LoginRepository
import ru.danilov.safestorage.domain.usecases.base.SingleUseCase
import javax.inject.Inject

class CreateStartFilesUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    fun execute() = loginRepository.createStartFiles()

}