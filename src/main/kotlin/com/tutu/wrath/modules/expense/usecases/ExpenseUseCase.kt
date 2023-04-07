package com.tutu.wrath.modules.expense.usecases

import com.tutu.wrath.modules.expense.model.Expense
import com.tutu.wrath.modules.expense.provider.ExpenseRepository

class ExpenseUseCase(private val repository: ExpenseRepository) {

    suspend fun getExpenses(): Result<List<Expense>> {
        return repository.getExpenses()
    }
}