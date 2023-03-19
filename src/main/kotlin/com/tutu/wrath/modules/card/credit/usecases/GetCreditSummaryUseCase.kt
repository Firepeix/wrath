package com.tutu.wrath.modules.card.credit.usecases

import com.tutu.wrath.modules.card.credit.model.CreditSummary

class GetCreditSummaryUseCase {

    fun getCreditSummary(): Result<List<CreditSummary>> {
        return Result.success(emptyList())
    }
}