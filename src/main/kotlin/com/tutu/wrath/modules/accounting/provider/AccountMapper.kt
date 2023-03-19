package com.tutu.wrath.modules.accounting.provider

import com.tutu.wrath.modules.accounting.dto.AccountResponse
import com.tutu.wrath.modules.accounting.models.Account
import com.tutu.wrath.modules.accounting.models.UnknownAccountType
import com.tutu.wrath.modules.user.dto.UserResponse
import com.tutu.wrath.modules.user.provider.UserMapper

class AccountMapper(private val userMapper: UserMapper) {

    fun toAccount(source: AccountResponse): Account {
        return when(source) {
            is UserResponse -> userMapper.toUser(source)
            else -> UnknownAccountType(source.id, source.name)
        }
    }
}