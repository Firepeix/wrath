package com.tutu.wrath.modules.income.usecases

import com.tutu.wrath.modules.income.models.Income
import com.tutu.wrath.modules.income.provider.IncomeRepository

class GetIncomeUseCase(private val repository: IncomeRepository) {

    suspend fun getIncome(): Result<List<Income>> {
        return repository.getIncome()
    }

}