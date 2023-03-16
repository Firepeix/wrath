package com.tutu.wrath.modules.user.models

import com.tutu.wrath.modules.accounting.models.Account
import kotlinx.serialization.Serializable

@Serializable
data class User(val name: String) : Account {
    override fun getName() = name
}