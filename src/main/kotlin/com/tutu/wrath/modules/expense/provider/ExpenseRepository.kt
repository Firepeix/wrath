package com.tutu.wrath.modules.expense.provider

import com.tutu.wrath.modules.expense.dto.ExpenseListResponse
import com.tutu.wrath.modules.expense.model.Expense
import com.tutu.wrath.util.Rocket

class ExpenseRepository(private val client: Rocket, private val mapper: ExpenseMapper) {
    suspend fun getExpenses() : Result<List<Expense>> {
        return client.get<ExpenseListResponse>("ebisu/expenses").map(mapper::toExpenseList)
    }
}