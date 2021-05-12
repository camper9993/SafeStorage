package ru.danilov.safestorage.domain.usecases.cryptography

import io.reactivex.Single
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.domain.repository.FileBrowserRepository
import ru.danilov.safestorage.domain.usecases.base.SingleUseCase
import javax.inject.Inject

class EncryptFileUseCase @Inject constructor(private val fileBrowserRepository: FileBrowserRepository) : SingleUseCase<PlainFile>() {

    private var sourcePath: String? = null


    fun setSourcePath(sourcePath: String) {
        this.sourcePath = sourcePath
    }

    private var destinationPath: String? = null


    fun setDestinationPath(destinationPath: String) {
        this.destinationPath = destinationPath
    }

    override fun buildUseCaseSingle(): Single<PlainFile> = fileBrowserRepository.addFile(sourcePath!!, destinationPath!!)

}