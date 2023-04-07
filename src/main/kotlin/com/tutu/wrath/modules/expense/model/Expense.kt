package com.tutu.wrath.modules.expense.model

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.anger.tables.RowValue
import com.tutu.wrath.modules.accounting.models.Account
import com.tutu.wrath.modules.card.shared.model.Card
import com.tutu.wrath.util.Chrono
import com.tutu.wrath.util.Money

data class Expense(
    val id: String,
    val payed: Boolean,
    val name: String,
    val amount: Money,
    val date: Chrono,
    val type: Type,
    val card: Card?,
    val source: Account?,
    val beneficiary: Account?,
    val installments: Installments?
): RowValue {
    enum class Type {
        DEBIT,
        CREDIT
    }

    data class Installments (
        val current: Int,
        val total: Int?
    ) {
        fun display() = Display( if (total == null) "$current" else "$current/$total")
    }

    override fun getRowValue(id: String, position: Int): Display {
        return when(id) {
            "name" -> Display(name)
            "amount" -> amount.display()
            "date" -> date.displayDate()
            "payed" -> Display(if(payed) "Sim" else "Não")
            "card.name" -> Display(card?.name ?: "-", color = card?.color)
            "beneficiary.name" -> Display(beneficiary?.getName() ?: "-")
            "installments" -> installments?.display() ?: super.getRowValue(id, position)
            "actions" -> Display("")
            else -> super.getRowValue(id, position)
        }
    }
}