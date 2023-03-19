package com.tutu.wrath.modules.user.model

import com.tutu.wrath.modules.accounting.models.Account

data class User(val id: String, val name: String) : Account {
    override fun getName() = name
}