package com.tutu.wrath.modules.income.provider

import com.tutu.wrath.modules.income.models.Income
import com.tutu.wrath.util.Rocket

class IncomeRepository(private val client: Rocket, private val mapper: IncomeMapper) {
    suspend fun getIncome() : Result<List<Income>> {
        return runCatching {
            TODO()
        }
    }
}