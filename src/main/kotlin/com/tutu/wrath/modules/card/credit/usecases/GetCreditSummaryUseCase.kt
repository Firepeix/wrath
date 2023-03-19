package com.tutu.wrath.modules.card.credit.usecases

import com.tutu.wrath.modules.card.credit.model.CreditSummary
import com.tutu.wrath.modules.card.shared.provider.CardRepository

class GetCreditSummaryUseCase(private val repository: CardRepository) {

    suspend fun getCreditSummary(): Result<List<CreditSummary>> {
        return repository.getCreditSummary()
    }
}