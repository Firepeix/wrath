package com.tutu.wrath.modules.card.shared.provider

import com.tutu.wrath.modules.card.credit.model.CreditSummary
import com.tutu.wrath.modules.card.shared.dto.CardResponse
import com.tutu.wrath.modules.card.shared.dto.CreditSummaryResponse
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