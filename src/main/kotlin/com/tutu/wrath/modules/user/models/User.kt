package com.tutu.wrath.modules.user.models

import com.tutu.wrath.modules.accounting.models.Account

data class User(val name: String) : Account {
    override fun getName() = name
}