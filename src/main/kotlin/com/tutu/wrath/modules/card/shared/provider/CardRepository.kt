package com.tutu.wrath.modules.card.shared.provider

import com.tutu.wrath.modules.card.credit.dto.CreditSummaryResponse
import com.tutu.wrath.modules.card.credit.model.CreditSummary
import com.tutu.wrath.modules.card.debit.dto.DebitSummaryResponse
import com.tutu.wrath.modules.card.debit.model.DebitSummary
import com.tutu.wrath.util.Rocket

class CardRepository(private val client: Rocket, private val mapper: CardMapper) {
    companion object {
        private const val CREDIT_SUMMARY_ENDPOINT = "ebisu/purchases/credit/summary"
        private const val DEBIT_SUMMARY_ENDPOINT = "ebisu/purchases/debit/summary"
        private const val SIMULATE_DEBIT_SUMMARY_ENDPOINT = "ebisu/purchases/debit/summary/simulate"
    }



    suspend fun getCreditSummary() : Result<List<CreditSummary>> {
        return client.get<CreditSummaryResponse>(CREDIT_SUMMARY_ENDPOINT).map(mapper::toCreditSummary)
    }

    suspend fun getDebitSummary() : Result<DebitSummary> {
        return client.get<DebitSummaryResponse>(DEBIT_SUMMARY_ENDPOINT).map(mapper::toDebitSummary)
    }

    suspend fun simulateDebitSummary(intoFuture: Int) : Result<DebitSummary> {
        return client.get<DebitSummaryResponse>("$SIMULATE_DEBIT_SUMMARY_ENDPOINT?future=$intoFuture").map(mapper::toDebitSummary)
    }
}