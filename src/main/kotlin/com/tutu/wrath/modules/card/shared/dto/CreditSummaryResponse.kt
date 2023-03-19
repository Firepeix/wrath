package com.tutu.wrath.modules.card.shared.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditSummaryResponse(val data: List<SummaryResponse>)

@Serializable
data class SummaryResponse(
    val card: CardResponse,
    val spent: Int,
    val planned: Int,
    val difference: Int,
    @SerialName("previous_installment_spent")
    val previousInstallmentSpent: Int,
)

