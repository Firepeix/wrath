package com.tutu.wrath.modules.user.dto

import com.tutu.wrath.modules.accounting.dto.AccountResponse
import kotlinx.serialization.Serializable


@Serializable
data class UserListResponse(val data: List<UserResponse>)
@Serializable
data class UserResponse(override val id: String, override val name: String) : AccountResponse