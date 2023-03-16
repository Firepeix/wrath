package com.tutu.wrath.modules.income.dto

import com.tutu.wrath.modules.accounting.models.Account
import com.tutu.wrath.modules.accounting.models.Frequency
import kotlinx.serialization.Serializable

@Serializable
data class IncomeListResponse(val data: List<IncomeResponse>)

@Serializable
data class IncomeResponse(
    val id: String,
    val name: String,
    val amount: Int,
    val date: String,
    val frequency: Frequency,
    val source: Account
)
