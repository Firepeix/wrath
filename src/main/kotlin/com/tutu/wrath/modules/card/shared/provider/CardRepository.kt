package com.tutu.wrath.modules.card.shared.provider

import com.tutu.wrath.modules.card.credit.model.CreditSummary
import com.tutu.wrath.modules.card.shared.dto.CreditSummaryResponse
import com.tutu.wrath.util.Rocket

class CardRepository(private val client: Rocket, private val mapper: CardMapper) {
    suspend fun getCreditSummary() : Result<List<CreditSummary>> {
        return client.get<CreditSummaryResponse>("ebisu/purchases/credit/summary").map(mapper::toCreditSummary)
    }
}