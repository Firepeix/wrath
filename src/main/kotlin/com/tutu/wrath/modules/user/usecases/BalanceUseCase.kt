package com.tutu.wrath.modules.user.usecases

import com.tutu.wrath.modules.user.model.UserBalance
import com.tutu.wrath.modules.user.provider.UserRepository

class BalanceUseCase(private val repository: UserRepository) {

    suspend fun getBalance(userId: String): Result<UserBalance> {
        return repository.getBalance(userId)
    }
}