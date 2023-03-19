package com.tutu.wrath.modules.income.provider

import com.tutu.wrath.modules.accounting.provider.AccountMapper
import com.tutu.wrath.modules.income.usecases.GetIncomeUseCase
import com.tutu.wrath.util.Rocket

class IncomeManager(rocket: Rocket, accountMapper: AccountMapper) {
    private val incomeMapper = IncomeMapper(accountMapper)
    private val incomeRepository = IncomeRepository(rocket, incomeMapper)
    val getIncomeUseCase = GetIncomeUseCase(incomeRepository)
}