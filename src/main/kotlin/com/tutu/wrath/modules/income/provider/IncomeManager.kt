package com.tutu.wrath.modules.income.provider

import com.tutu.wrath.modules.income.usecases.GetIncomeUseCase
import com.tutu.wrath.util.Rocket

class IncomeManager(rocket: Rocket) {
    private val incomeMapper = IncomeMapper()
    private val incomeRepository = IncomeRepository(rocket, incomeMapper)
    val getIncomeUseCase = GetIncomeUseCase(incomeRepository)
}