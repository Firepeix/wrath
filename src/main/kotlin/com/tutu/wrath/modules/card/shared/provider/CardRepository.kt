package com.tutu.wrath.modules.card.shared.provider

import com.tutu.wrath.modules.card.credit.dto.CreditSummaryResponse
import com.tutu.wrath.modules.card.credit.model.CreditSummary
import com.tutu.wrath.modules.card.debit.dto.DebitSummaryResponse
import com.tutu.wrath.modules.card.debit.model.DebitSummary
import com.tutu.wrath.util.Rocket

class CardRepository(private val client: Rocket, private val mapper: CardMapper) {
    suspend fun getCreditSummary() : Result<List<CreditSummary>> {
        return client.get<CreditSummaryResponse>("ebisu/purchases/credit/summary").map(mapper::toCreditSummary)
    }

    suspend fun getDebitSummary() : Result<DebitSummary> {
        return client.get<DebitSummaryResponse>("ebisu/purchases/debit/summary").map(mapper::toDebitSummary)
    }
}