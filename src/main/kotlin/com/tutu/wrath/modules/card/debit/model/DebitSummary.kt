package com.tutu.wrath.modules.card.debit.model

import com.tutu.wrath.util.Money

data class DebitSummary(
    val currentNetAmount: Money,
    val amountToPay: Money,
    val payedAmount: Money,
    val totalAmount: Money,
    val currentAmount: Money,
    val forecastAmount: Money,
)
