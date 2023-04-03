package com.tutu.wrath.modules.card.shared.provider

import com.tutu.wrath.modules.card.credit.dto.CreditSummaryResponse
import com.tutu.wrath.modules.card.credit.model.CreditSummary
import com.tutu.wrath.modules.card.debit.dto.DebitSummaryResponse
import com.tutu.wrath.modules.card.debit.model.DebitSummary
import com.tutu.wrath.modules.card.shared.dto.CardResponse
import com.tutu.wrath.modules.card.shared.model.Card
import com.tutu.wrath.util.Money
import io.kvision.core.Color

class CardMapper {

    fun toCreditSummary(source: CreditSummaryResponse) : List<CreditSummary> {
        return source.data.map {
            CreditSummary(
                card = toCard(it.card),
                spent = Money(amount = it.spent + it.previousInstallmentSpent),
                planned = Money(amount = it.planned),
                difference = Money(amount = it.difference),
            )
        }
    }

    fun toDebitSummary(source: DebitSummaryResponse): DebitSummary {
        val (summary) = source
        return DebitSummary(
            currentNetAmount = Money(summary.currentAmount),
            amountToPay = Money(summary.amountToPay),
            payedAmount = Money(summary.payedAmount),
            totalAmount = Money(summary.totalAmount),
            forecastAmount = Money(summary.forecastAmount),
            currentAmount = Money(summary.currentAmount),
        )
    }

    fun toCard(source: CardResponse): Card {
        return Card(
            id = source.id,
            name = source.name,
            budget = Money(amount = source.budget),
            color = Color.rgb(
                red = source.color["red"] ?: 0,
                green = source.color["green"] ?: 0,
                blue = source.color["blue"] ?: 0
            )
        )
    }
}