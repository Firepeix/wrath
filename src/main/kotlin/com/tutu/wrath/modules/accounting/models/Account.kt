package com.tutu.wrath.modules.accounting.models

import com.tutu.wrath.modules.accounting.repository.AccountSerde
import kotlinx.serialization.Serializable

@Serializable(AccountSerde::class)
interface Account {
    fun getName() : String
}