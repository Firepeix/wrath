package com.tutu.wrath.modules.card.debit.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DebitSummaryResponse(val data: SummaryResponse)

@Serializable
data class SummaryResponse(
    @SerialName("current_net_amount")
    val currentNetAmount: Int,

    @SerialName("amount_to_pay")
    val amountToPay: Int,

    @SerialName("payed_amount")
    val payedAmount: Int,

    @SerialName("total_amount")
    val totalAmount: Int,

    @SerialName("forecast_amount")
    val forecastAmount: Int,

    @SerialName("current_amount")
    val currentAmount: Int,
)