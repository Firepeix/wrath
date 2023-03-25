package com.tutu.wrath.modules.user.dto

import com.tutu.wrath.modules.expense.dto.ExpenseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserBalanceDataResponse(val data: UserBalanceResponse)

@Serializable
data class UserBalanceResponse(

    @SerialName("balance_total")
    val balanceTotal: Int,

    @SerialName("user_beneficiary_expenses")
    val userBeneficiaryExpenses: List<ExpenseResponse>,

    @SerialName("friend_beneficiary_expenses")
    val friendBeneficiaryExpenses: List<ExpenseResponse>
)