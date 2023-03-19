package com.tutu.wrath.modules.card.shared.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardResponse(
    val id: String,
    val name: String,
    val budget: Int,
    @SerialName("due_date")
    val dueDate: String,
    @SerialName("close_date")
    val closeDate: String,
    val color: Map<String, Int>,
    val sisters: Set<CardResponse>
)