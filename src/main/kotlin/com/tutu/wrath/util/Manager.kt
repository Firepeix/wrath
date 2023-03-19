package com.tutu.wrath.util

import com.tutu.wrath.modules.accounting.provider.AccountManager
import com.tutu.wrath.modules.card.shared.provider.CardManager
import com.tutu.wrath.modules.income.provider.IncomeManager
import com.tutu.wrath.modules.user.provider.UserManager

class Manager {
    private val rocket = Rocket()

    val userManager = UserManager(rocket)
    val accountManager = AccountManager(userManager.mapper)
    val incomeManager = IncomeManager(rocket, accountManager.accountMapper)
    val cardManager = CardManager(rocket)
}