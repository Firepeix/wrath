package com.tutu.wrath.modules.income.provider

import com.tutu.wrath.modules.accounting.provider.AccountMapper
import com.tutu.wrath.modules.income.dto.IncomeListResponse
import com.tutu.wrath.modules.income.models.Income
import com.tutu.wrath.util.Chrono
import com.tutu.wrath.util.Money

class IncomeMapper(private val accountMapper: AccountMapper) {

    fun toIncomeList(source: IncomeListResponse) : List<Income> {
        return source.data.map {
            Income(
                name = it.name,
                amount = Money(amount = it.amount),
                origin = it.source.run(accountMapper::toAccount),
                data = Chrono(it.date),
                frequency = it.frequency
            )
        }
    }
}