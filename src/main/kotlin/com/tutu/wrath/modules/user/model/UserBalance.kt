package com.tutu.wrath.modules.user.model

import com.tutu.wrath.modules.expense.model.Expense
import com.tutu.wrath.util.Money

data class UserBalance(
    val totalAmount: Money,
    val userBeneficiaryExpenses: List<Expense>,
    val friendBeneficiaryExpenses: List<Expense>
)

