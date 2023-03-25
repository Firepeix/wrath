package com.tutu.wrath.modules.user.provider

import com.tutu.wrath.modules.expense.dto.ExpenseResponse
import com.tutu.wrath.modules.expense.model.Expense
import com.tutu.wrath.modules.user.usecases.BalanceUseCase
import com.tutu.wrath.modules.user.usecases.UserUseCase
import com.tutu.wrath.util.Rocket

typealias MapExpense = (source: ExpenseResponse) -> Expense

class UserManager(rocket: Rocket, mapExpense: MapExpense) {
    val mapper = UserMapper(mapExpense)
    val repository = UserRepository(rocket, mapper)
    val useCase = UserUseCase(repository)
    val balanceUseCase = BalanceUseCase(repository)
}