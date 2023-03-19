package com.tutu.wrath.modules.card.shared.provider

import com.tutu.wrath.modules.card.credit.usecases.GetCreditSummaryUseCase
import com.tutu.wrath.util.Rocket

class CardManager(rocket: Rocket) {
    //private val incomeMapper = IncomeMapper()
    //private val incomeRepository = IncomeRepository(rocket, incomeMapper)
    val getCreditSummaryUseCase = GetCreditSummaryUseCase()
}