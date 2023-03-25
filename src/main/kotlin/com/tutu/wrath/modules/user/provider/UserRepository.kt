package com.tutu.wrath.modules.user.provider

import com.tutu.wrath.modules.user.dto.UserBalanceDataResponse
import com.tutu.wrath.modules.user.dto.UserListResponse
import com.tutu.wrath.modules.user.model.User
import com.tutu.wrath.modules.user.model.UserBalance
import com.tutu.wrath.util.Rocket

class UserRepository(private val client: Rocket, private val mapper: UserMapper) {
    suspend fun getFriends() : Result<List<User>> {
        return client.get<UserListResponse>("ebisu/users/friends").map(mapper::toUserList)
    }

    suspend fun getBalance(userId: String) : Result<UserBalance> {
        return client.get<UserBalanceDataResponse>("ebisu/users/friends/$userId/balance").map(mapper::toUserBalance)
    }
}