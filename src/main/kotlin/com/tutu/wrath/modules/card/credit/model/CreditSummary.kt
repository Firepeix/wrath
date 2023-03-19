package com.tutu.wrath.modules.card.credit.model

import com.tutu.wrath.anger.tables.RowValue
import com.tutu.wrath.modules.card.shared.model.Card
import com.tutu.wrath.util.Money

data class CreditSummary(
    val card: Card,
    val spent: Money,
    val planned: Money,
    val difference: Money
) : RowValue {
    override fun getRowValue(id: String): String {
        return when(id) {
            "card.name" -> card.name
            "spent" -> spent.display()
            "planned" -> planned.display()
            "difference" -> difference.display()
            else -> super.getRowValue(id)
        }
    }
}
