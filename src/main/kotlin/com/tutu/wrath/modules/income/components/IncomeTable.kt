package com.tutu.wrath.modules.income.components

import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.table
import com.tutu.wrath.modules.income.models.Income
import com.tutu.wrath.modules.income.usecases.GetIncomeUseCase
import com.tutu.wrath.util.unwrap
import io.kvision.core.Container
import io.kvision.html.Div
import io.kvision.snabbdom.VNode
import io.kvision.state.ObservableValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IncomeTable(private val useCase: GetIncomeUseCase) : Div(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private val columns = listOf(
        Column("name", "Nome"),
        Column("amount", "Valor"),
        Column("origin", "Origem")
    )

    private val incomes = ObservableValue(emptyList<Income>())
    private val rows = ObservableValue(emptyList<Row>())

    init {
        table(header = "Entradas", columns = columns, rows = rows)
    }

    override fun afterInsert(node: VNode) {
        setHooks()
        initialize()
    }

    private fun setHooks() {
        incomes.subscribe { incomes -> rows.setState(incomes.map { Row(it) }) }
    }

    private fun initialize() {
        launch {
            incomes.setState(useCase.getIncome().unwrap(emptyList()))
        }
    }

}

fun Container.incomeTable(getIncomeUseCase: GetIncomeUseCase) : IncomeTable {
    val component = IncomeTable(getIncomeUseCase)
    this.add(component)
    return component
}

