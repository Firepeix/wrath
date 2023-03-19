package com.tutu.wrath.modules.card.shared.model

import com.tutu.wrath.util.Money
import io.kvision.core.Color

data class Card(
    val id: String,
    val name: String,
    val budget: Money,
    val color: Color
)
