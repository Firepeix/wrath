package com.tutu.wrath.anger.layout

import com.tutu.wrath.anger.button.primaryCTAButton
import com.tutu.wrath.anger.card.pageHeading
import com.tutu.wrath.anger.modal.modal
import com.tutu.wrath.modules.card.credit.components.creditSummaryTable
import com.tutu.wrath.modules.income.components.incomeTable
import com.tutu.wrath.modules.user.components.userBalanceTable
import com.tutu.wrath.util.Manager
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.main as baseMain

fun Container.main(manager: Manager) {
    val summaryModel = modal("summary-modal")

    baseMain(className = "p-8 px-4") {
        pageHeading("Despesas") {
            primaryCTAButton("Ver Resumo") { summaryModel.toggle() }
        }

        div(className = "grid gap-4 grid-cols-3 py-4"){
            incomeTable(manager.incomeManager.getIncomeUseCase)
            creditSummaryTable(manager.cardManager.getCreditSummaryUseCase)
            userBalanceTable(manager.userManager.useCase::getFriends, manager.userManager.balanceUseCase::getBalance, manager.userManager.balanceUseCase::createRows)
        }

    }
}