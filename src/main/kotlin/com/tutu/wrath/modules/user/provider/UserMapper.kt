package com.tutu.wrath.modules.user.provider

import com.tutu.wrath.modules.user.dto.UserBalanceDataResponse
import com.tutu.wrath.modules.user.dto.UserListResponse
import com.tutu.wrath.modules.user.dto.UserResponse
import com.tutu.wrath.modules.user.model.User
import com.tutu.wrath.modules.user.model.UserBalance
import com.tutu.wrath.util.Money

class UserMapper(private val mapExpense: MapExpense) {

    fun toUser(source: UserResponse): User {
        return User(source.id, source.name)
    }

    fun toUserList(source: UserListResponse): List<User> {
        return source.data.map(::toUser)
    }

    fun toUserBalance(source: UserBalanceDataResponse): UserBalance {
        return UserBalance(
            totalAmount = Money(source.data.balanceTotal),
            userBeneficiaryExpenses = source.data.userBeneficiaryExpenses.map(mapExpense),
            friendBeneficiaryExpenses = source.data.friendBeneficiaryExpenses.map(mapExpense)
        )
    }
}