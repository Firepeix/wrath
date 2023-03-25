package com.tutu.wrath.modules.expense.dto

import com.tutu.wrath.modules.accounting.dto.AccountResponse
import com.tutu.wrath.modules.card.shared.dto.CardResponse
import com.tutu.wrath.modules.expense.model.Expense
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseResponse(
    val id: String,
    val payed: Boolean,
    val name: String,
    val amount: Int,
    val date: String,
    val type: Expense.Type,
    val card: CardResponse?,
    val source: AccountResponse?,
    val beneficiary: AccountResponse?,
    val installments: InstallmentsResponse?
) {
    @Serializable
    data class InstallmentsResponse (
        val current: Int,
        val total: Int?
    )
}
