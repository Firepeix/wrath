package com.tutu.wrath.modules.accounting.provider

import com.tutu.wrath.modules.user.provider.UserMapper

class AccountManager(userMapper: UserMapper) {
    val accountMapper = AccountMapper(userMapper)
}