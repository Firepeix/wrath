package com.tutu.wrath.anger.layout

import com.tutu.wrath.anger.button.primaryCTAButton
import com.tutu.wrath.anger.card.pageHeading
import com.tutu.wrath.modules.card.credit.components.creditSummaryTable
import com.tutu.wrath.modules.income.components.incomeTable
import com.tutu.wrath.modules.user.components.userBalanceTable
import com.tutu.wrath.util.Manager
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.main as baseMain

fun Container.main(manager: Manager) {
    baseMain(className = "p-8 px-4") {
        pageHeading("Despesas") {
            primaryCTAButton("Ver Resumo") {
                console.log("asd")
            }
        }

        div(className = "grid gap-4 grid-cols-3 py-4"){
            incomeTable(manager.incomeManager.getIncomeUseCase)
            creditSummaryTable(manager.cardManager.getCreditSummaryUseCase)
            userBalanceTable(manager.userManager.useCase)
        }
    }
}