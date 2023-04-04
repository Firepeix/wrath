package com.tutu.wrath.modules.card.debit.components

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.anger.form.select.Select
import com.tutu.wrath.anger.form.select.select
import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.DisplayRow
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.table
import com.tutu.wrath.modules.card.debit.model.DebitSummary
import com.tutu.wrath.util.Chrono
import com.tutu.wrath.util.unwrap
import io.kvision.core.Container
import io.kvision.html.Div
import io.kvision.html.div
import io.kvision.state.ObservableValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias GetDebitSummary = suspend () -> Result<DebitSummary>

class DebitSummaryTable(private val getSummary: GetDebitSummary,) : Div(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private var isMounted = false
    private var isLoaded = false
    private var monthSelect: Select<Chrono.Month>? = null
    private val columns = listOf(Column("debits", "Debitos", colspan = 2))
    private val rows = ObservableValue(emptyList<Row>())
    private val summary: ObservableValue<DebitSummary?> = ObservableValue(null)
    private val month: ObservableValue<Chrono.Month?> = ObservableValue(Chrono.now().month)

    init {
        val self = this
        div(className = "shadow-none flex flex-shrink-0 items-center justify-between border-neutral-100 border-b border-opacity-100 p-3 dark:border-opacity-50") {
            self.monthSelect = select(self.month, Chrono.Month.values().asList(), "Mes Vigente", startInit = false)
        }
        table(columns, rows, flat = true, showColumns = false)
    }

    fun initialize() {
        if (!isMounted) setHooks()
        construct()
    }

    private fun construct() {
        launch {
            summary.setState(getSummary().unwrap(null))
            monthSelect?.initialize()
            isLoaded = true
        }
    }

    private fun setHooks() {
        summary.subscribe { summary -> summary?.let { rows.setState(createRows(it)) } }
        isMounted = true
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

