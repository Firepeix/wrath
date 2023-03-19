package com.tutu.wrath.modules.card.credit.model

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.anger.tables.RowValue
import com.tutu.wrath.modules.card.shared.model.Card
import com.tutu.wrath.util.Money
import io.kvision.core.CssSize
import io.kvision.core.FontWeight
import io.kvision.core.UNIT

data class CreditSummary(
    val card: Card,
    val spent: Money,
    val planned: Money,
    val difference: Money
) : RowValue {
    override fun getRowValue(id: String): Display {
        return when(id) {
            "card.name" -> Display(card.name, color = card.color, weight = FontWeight.BOLD, size = CssSize(1.2, UNIT.rem))
            "spent" -> spent.display()
            "planned" -> planned.display()
            "difference" -> difference.display(true)
            else -> super.getRowValue(id)
        }
    }
}
