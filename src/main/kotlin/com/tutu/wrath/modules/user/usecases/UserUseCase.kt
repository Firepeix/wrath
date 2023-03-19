package com.tutu.wrath.modules.user.usecases

import com.tutu.wrath.modules.user.model.User
import com.tutu.wrath.modules.user.provider.UserRepository

class UserUseCase(private val repository: UserRepository) {

    suspend fun getBalance() {

    }

    suspend fun getFriends(): Result<List<User>> {
        return repository.getFriends()
    }
}