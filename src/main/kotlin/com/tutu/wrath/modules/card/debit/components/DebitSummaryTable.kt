package com.tutu.wrath.modules.card.debit.components

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.anger.form.select.Select
import com.tutu.wrath.anger.form.select.select
import com.tutu.wrath.anger.tables.*
import com.tutu.wrath.anger.tables.Table.Companion.table
import com.tutu.wrath.modules.card.debit.model.DebitSummary
import com.tutu.wrath.util.*
import com.tutu.wrath.util.Chrono.Month
import io.kvision.core.Container
import io.kvision.html.Div
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias GetDebitSummary = suspend (month: Month?) -> Result<DebitSummary>

private object Listeners {
    var onSummaryChange: ((DebitSummary?) -> Unit)? = null
    var onMonthChange: ((Month?) -> Unit)? = null
    var onLoadedChange: ((Boolean) -> Unit)? = null
}

private class State(init: Listeners.(State) -> Unit) {
    val month: VModel<Month?> = model(Chrono.now().month) { Listeners.onMonthChange?.invoke(it) }

    var isLoaded by observable(false) { Listeners.onLoadedChange?.invoke(it) }
    var isMounted = false
    var summary by observable<DebitSummary?>(null) { Listeners.onSummaryChange?.invoke(it) }

    init {
        init.invoke(Listeners, this)
    }
}

class DebitSummaryTable(private val getSummary: GetDebitSummary,) : Div(), CoroutineScope by CoroutineScope(Dispatchers.Default) {
    private val state = State {
        onLoadedChange = ::onLoadedChanged
        onSummaryChange = ::onSummaryChanged
        onMonthChange = ::onMonthChanged
    }

    private var table: TableScope? = null
    private var select: Select<Month>? = null

    init {
        div("shadow-none flex flex-shrink-0 items-center justify-between border-neutral-100 border-b border-opacity-100 p-3 dark:border-opacity-50") {
            select = select(
                value = state.month,
                parent = it,
                properties = Select.Properties(options = Month.values().asList(), isLoading = true),
                attributes = Select.Attributes(label = "Mes Vigente", lateInit = true),
            )
        }

        table = table(
            properties = Table.Properties(Column("debits", "Debitos", colspan = 2), rows = emptyList(), isLoading = true),
            attributes = Table.Attributes(flat = true, showColumns = false, estimatedRows = 6)
        )
    }

    fun initialize() {
        construct()
    }

    private fun construct() {
        launch {
            state.month.setState(Chrono.now().month)
            load(Chrono.now().month)
            select?.change { it.initialize() }
            state.isMounted = true
        }
    }

    private suspend fun load(month: Month?) {
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

    private fun onLoadedChanged(loaded: Boolean) {
        select?.change { isLoading = !loaded && !state.isMounted }
        table?.change { isLoading = !loaded }
    }

    private fun onSummaryChanged(summary: DebitSummary?) {
        if (summary != null) {
            table?.change {
                rows = createRows(summary)
            }
        }
    }

    private fun onMonthChanged(month: Month?) {
        launch {
            if(state.isLoaded) {
                load(month)
            }
        }
    }


}

fun Container.debitSummary(getSummary: GetDebitSummary) : DebitSummaryTable {
    val component = DebitSummaryTable(getSummary)
    this.add(component)
    return component
}

