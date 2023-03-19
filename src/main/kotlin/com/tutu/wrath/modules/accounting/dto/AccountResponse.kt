package com.tutu.wrath.modules.accounting.dto

import com.tutu.wrath.modules.accounting.provider.AccountSerde
import kotlinx.serialization.Serializable

@Serializable(AccountSerde::class)
interface AccountResponse {
    val id: String
    val name: String
}

@Serializable
data class UnknownAccountTypeResponse(override val id: String, override val name: String): AccountResponse