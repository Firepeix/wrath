package com.tutu.wrath.modules.income.provider

import com.tutu.wrath.modules.income.dto.IncomeListResponse
import com.tutu.wrath.modules.income.models.Income
import com.tutu.wrath.util.Chrono
import com.tutu.wrath.util.Money

class IncomeMapper {//moment

    fun toIncomeList(source: IncomeListResponse) : List<Income> {
        return source.data.map {
            Income(
                name = it.name,
                amount = Money(amount = it.amount),
                origin = it.source,
                data = Chrono(it.date),
                frequency = it.frequency
            )
        }
    }
}