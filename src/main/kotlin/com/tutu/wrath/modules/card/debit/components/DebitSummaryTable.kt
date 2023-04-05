package com.tutu.wrath.modules.card.debit.components

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.anger.form.select.Select
import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.DisplayRow
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.Table
import com.tutu.wrath.modules.card.debit.model.DebitSummary
import com.tutu.wrath.util.*
import io.kvision.core.Container
import io.kvision.html.Div
import io.kvision.html.div
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias GetDebitSummary = suspend (month: Chrono.Month?) -> Result<DebitSummary>

private object Listeners {
    var onSummaryChange: ((DebitSummary?) -> Unit)? = null
    var onMonthChange: ((Chrono.Month?) -> Unit)? = null
    var onLoadedChange: ((Boolean) -> Unit)? = null
}

private class State(init: Listeners.(State) -> Unit) {
    val month: VModel<Chrono.Month?> = model(Chrono.now().month) { Listeners.onMonthChange?.invoke(it) }

    var isLoaded by observable(false) { Listeners.onLoadedChange?.invoke(it) }
    var isMounted = false
    var summary by observable<DebitSummary?>(null) { Listeners.onSummaryChange?.invoke(it) }

    init {
        init.invoke(Listeners, this)
    }
}

class DebitSummaryTable(private val getSummary: GetDebitSummary,) : Div(), CoroutineScope by CoroutineScope(Dispatchers.Default) {
    private val state: State = State { state ->
        onLoadedChange = { select.isLoading = !it && !state.isMounted }
        onSummaryChange = { it?.let { table.rows = createRows(it) } }
        onMonthChange = { launch { if(state.isLoaded) load(it) } }
    }

    private val table = Table(listOf(Column("debits", "Debitos", colspan = 2)), emptyList(), flat = true, showColumns = false)
    private val select = Select(state.month, Chrono.Month.values().asList(), label = "Mes Vigente", lateInit = true, isLoading = true)

    init {
        val self = this

        div(className = "shadow-none flex flex-shrink-0 items-center justify-between border-neutral-100 border-b border-opacity-100 p-3 dark:border-opacity-50") {
            add(self.select)
        }

        add(table)
    }

    fun initialize() {
        construct()
    }

    private fun construct() {
        launch {
            state.month.setState(Chrono.now().month)
            load(Chrono.now().month)
            select.initialize()
            state.isMounted = true
        }
    }

    private suspend fun load(month: Chrono.Month?) {
        state.isLoaded = false
        state.summary = getSummary(month).unwrap(null)
        state.isLoaded = true
    }

    private fun createRows(summary: DebitSummary): List<Row> {
        return listOf(
            DisplayRow(Display("Valor Liquido Atual"), summary.currentNetAmount.display()),
            DisplayRow(Display("Valor à Pagar"), summary.amountToPay.display()),
            DisplayRow(Display("Valor Pago"), summary.payedAmount.display()),
            DisplayRow(Display("Valor Total"), summary.totalAmount.display()),
            DisplayRow(Display("Valor Restante"), summary.currentAmount.display()),
            DisplayRow(Display("Valor Previsto"), summary.forecastAmount.display()),
        ).map { Row(it) }
    }
}

fun Container.debitSummary(getSummary: GetDebitSummary) : DebitSummaryTable {
    val component = DebitSummaryTable(getSummary)
    this.add(component)
    return component
}

