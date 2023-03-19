package com.tutu.wrath.modules.accounting.models


interface Account {
    fun getName() : String
}

data class UnknownAccountType(val id: String, val name: String): Account {
    override fun getName() = name
}