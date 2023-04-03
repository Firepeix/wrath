package com.tutu.wrath.modules.card.debit.usecases

import com.tutu.wrath.modules.card.debit.model.DebitSummary
import com.tutu.wrath.modules.card.shared.provider.CardRepository

class DebitSummaryUseCase(private val repository: CardRepository) {

    suspend fun getSummary(): Result<DebitSummary> {
        return repository.getDebitSummary()
    }
}