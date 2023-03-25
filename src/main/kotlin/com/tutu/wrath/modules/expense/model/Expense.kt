package com.tutu.wrath.modules.expense.model

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
) {
    enum class Type {
        DEBIT,
        CREDIT
    }

    data class Installments (
        val current: Int,
        val total: Int?
    )
}