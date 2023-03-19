package com.tutu.wrath.modules.income.models

import com.tutu.wrath.anger.tables.RowValue
import com.tutu.wrath.modules.accounting.models.Account
import com.tutu.wrath.modules.accounting.models.Frequency
import com.tutu.wrath.util.Chrono
import com.tutu.wrath.util.Money

data class Income(
    val name: String,
    val amount: Money,
    val origin: Account,
    val data: Chrono,
    val frequency: Frequency
) : RowValue {

    override fun getRowValue(id: String): String {
        return when(id) {
            "name" -> name
            "amount" -> amount.display()
            "origin" -> origin.getName()
            else -> "-"
        }
    }
}

