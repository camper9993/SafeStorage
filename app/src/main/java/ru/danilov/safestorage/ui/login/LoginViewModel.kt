package ru.danilov.safestorage.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.danilov.safestorage.domain.usecases.login.CheckPasswordUseCase
import ru.danilov.safestorage.domain.usecases.login.CreateStartFilesUseCase

class LoginViewModel @ViewModelInject constructor(val checkPasswordUseCase: CheckPasswordUseCase, val createStartFilesUseCase: CreateStartFilesUseCase) : ViewModel(){

    val password = MutableLiveData<String>()
    val repeatPassword = MutableLiveData<String>()
    val isPasswordCorrect = MutableLiveData<Boolean>()


    fun setIsPasswordCorrect() {
        checkPasswordUseCase.execute(
            onSuccess = {
                isPasswordCorrect.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun createStartFiles() {
        createStartFilesUseCase.execute()
    }

    fun setPassword(password : String) {
        this.password.value = password
    }

    fun setRepeatPassword(password : String) {
        repeatPassword.value = password
    }


}