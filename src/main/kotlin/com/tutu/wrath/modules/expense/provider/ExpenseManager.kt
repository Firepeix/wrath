package com.tutu.wrath.modules.expense.provider

import com.tutu.wrath.modules.accounting.provider.AccountMapper
import com.tutu.wrath.modules.card.shared.provider.CardMapper
import com.tutu.wrath.modules.expense.usecases.ExpenseUseCase
import com.tutu.wrath.util.Rocket

class ExpenseManager(rocket: Rocket, accountMapper: AccountMapper, cardMapper: CardMapper) {
    val mapper = ExpenseMapper(cardMapper, accountMapper)

    private val repository = ExpenseRepository(rocket, mapper)

    val expenseUseCase = ExpenseUseCase(repository)
}