package com.tutu.wrath.modules.user.usecases

import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.modules.user.model.UserBalance
import com.tutu.wrath.modules.user.model.UserBeneficiaryExpense
import com.tutu.wrath.modules.user.provider.UserRepository

class BalanceUseCase(private val repository: UserRepository) {

    suspend fun getBalance(userId: String): Result<UserBalance> {
        return repository.getBalance(userId)
    }

    fun createRows(balance: UserBalance): List<Row> {
        val rows = mutableListOf<Row>()
        for (index in 0..getMaxRows(balance)) {
            val row = UserBeneficiaryExpense(balance.friendBeneficiaryExpenses.getOrNull(index), balance.userBeneficiaryExpenses.getOrNull(index))
            rows.add(Row(row))
        }

        return rows
    }

    private fun getMaxRows(balance: UserBalance): Int {
        val count = if (balance.friendBeneficiaryExpenses.size > balance.userBeneficiaryExpenses.size) balance.friendBeneficiaryExpenses.size
        else balance.userBeneficiaryExpenses.size
        return count - 1
    }
}