package com.tutu.wrath.modules.card.credit.components

import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.tableDeprecated
import com.tutu.wrath.modules.card.credit.model.CreditSummary
import com.tutu.wrath.modules.card.credit.usecases.GetCreditSummaryUseCase
import com.tutu.wrath.util.unwrap
import io.kvision.core.Container
import io.kvision.html.Div
import io.kvision.snabbdom.VNode
import io.kvision.state.ObservableValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreditSummaryTable(private val useCase: GetCreditSummaryUseCase) : Div(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private val columns = listOf(
        Column("card.name", "Cartão"),
        Column("spent", "Gasto"),
        Column("planned", "Planejado"),
        Column("difference", "Diferença")
    )

    private val summaries = ObservableValue(emptyList<CreditSummary>())
    private val rows = ObservableValue(emptyList<Row>())

    init {
        tableDeprecated(header = "Resumo Compras", columns = columns, rows = rows)
    }

    override fun afterInsert(node: VNode) {
        setHooks()
        initialize()
    }

    private fun setHooks() {
        summaries.subscribe { summaries -> rows.setState(summaries.map { Row(it) }) }
    }

    private fun initialize() {
        launch {
            summaries.setState(useCase.getCreditSummary().unwrap(emptyList()))
        }
    }

}

fun Container.creditSummaryTable(useCase: GetCreditSummaryUseCase) : CreditSummaryTable {
    val component = CreditSummaryTable(useCase)
    this.add(component)
    return component
}

