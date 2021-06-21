package ru.danilov.safestorage.data.zip

import io.reactivex.Single
import net.lingala.zip4j.ZipFile
import ru.danilov.safestorage.domain.repository.ZipRepository
import java.io.File

class ZipRepositoryImpl : ZipRepository {

    val zipManager  = ZipManager

    override fun zipFile(files: File, zipFile: File) : Single<File>{
        zipManager.zip(files, zipFile)
        return Single.just(zipFile)
    }

    override fun unzipFile(path: String, zipFile: ZipFile): Single<File> {
        zipManager.unzip(path, zipFile)
        return Single.just(File(path))
    }

}