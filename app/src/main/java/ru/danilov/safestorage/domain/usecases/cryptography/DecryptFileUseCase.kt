//package ru.danilov.safestorage.domain.usecases.cryptography
//
//import ru.danilov.safestorage.domain.repository.CryptographyRepository
//import ru.danilov.safestorage.domain.usecases.base.SingleUseCase
//import javax.inject.Inject
//
//
//class AddFileUseCase @Inject constructor(private val cryptographyRepository: CryptographyRepository) : SingleUseCase<Any>() {
//
//    private var sourcePath: String? = null
//
//
//    fun setSourcePath(sourcePath: String) {
//        this.sourcePath = sourcePath
//    }
//
//    override fun buildUseCaseSingle() = cryptographyRepository.decryptFile()
//
//}