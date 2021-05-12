package ru.danilov.safestorage.domain.usecases.cryptography

import ru.danilov.safestorage.domain.repository.CryptographyRepository
import ru.danilov.safestorage.domain.usecases.base.SingleUseCase
import ru.danilov.safestorage.models.PlainFile
import java.io.File
import javax.inject.Inject


class DecryptFileUseCase @Inject constructor(private val cryptographyRepository: CryptographyRepository) : SingleUseCase<PlainFile>() {

    private var sourcePath: String? = null


    fun setSourcePath(sourcePath: String) {
        this.sourcePath = sourcePath
    }

    override fun buildUseCaseSingle() = cryptographyRepository.decryptFile(File(sourcePath))

}