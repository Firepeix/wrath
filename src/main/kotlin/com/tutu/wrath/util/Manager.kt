package com.tutu.wrath.util

import com.tutu.wrath.modules.card.shared.provider.CardManager
import com.tutu.wrath.modules.income.provider.IncomeManager

class Manager {
    private val rocket = Rocket()

    val incomeManager = IncomeManager(rocket)
    val cardManager = CardManager(rocket)
}