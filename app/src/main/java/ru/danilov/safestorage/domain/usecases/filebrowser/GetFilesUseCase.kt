package ru.danilov.safestorage.domain.usecases.filebrowser

import io.reactivex.Single
import ru.danilov.safestorage.models.PlainFile
import ru.danilov.safestorage.domain.repository.FileBrowserRepository
import ru.danilov.safestorage.domain.usecases.base.SingleUseCase
import javax.inject.Inject

class GetFilesUseCase @Inject constructor(private val fileBrowserRepository: FileBrowserRepository) : SingleUseCase<List<PlainFile>>() {

    private var path: String? = null


    fun setPath(path: String) {
        this.path = path
    }

    override fun buildUseCaseSingle(): Single<List<PlainFile>> = fileBrowserRepository.getFiles(path!!)


}