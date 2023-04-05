package com.tutu.wrath.modules.card.debit.usecases

import com.tutu.wrath.modules.card.debit.model.DebitSummary
import com.tutu.wrath.modules.card.shared.provider.CardRepository
import com.tutu.wrath.util.Chrono

class DebitSummaryUseCase(private val repository: CardRepository) {

    suspend fun getSummary(month: Chrono.Month? = null): Result<DebitSummary> {
        val currentMonth = Chrono.now().month

        if (month == null) {
            return repository.getDebitSummary()
        }

        val intoFuture = currentMonth.distance(month)

        if (intoFuture == 0) {
            return repository.getDebitSummary()
        }

        return repository.simulateDebitSummary(intoFuture)
    }
}