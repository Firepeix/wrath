package com.tutu.wrath.util

import com.tutu.wrath.modules.accounting.provider.AccountManager
import com.tutu.wrath.modules.card.shared.provider.CardManager
import com.tutu.wrath.modules.expense.dto.ExpenseResponse
import com.tutu.wrath.modules.expense.model.Expense
import com.tutu.wrath.modules.expense.provider.ExpenseManager
import com.tutu.wrath.modules.income.provider.IncomeManager
import com.tutu.wrath.modules.user.provider.UserManager

class Manager {
    private val rocket = Rocket()
    val cardManager = CardManager(rocket)
    val userManager = UserManager(rocket, ::toExpense)
    val accountManager = AccountManager(userManager.mapper)

    val expenseManager = ExpenseManager(rocket, accountManager.accountMapper, cardManager.mapper)
    val incomeManager = IncomeManager(rocket, accountManager.accountMapper)

    fun toExpense(source: ExpenseResponse): Expense = expenseManager.mapper.toExpense(source)
}