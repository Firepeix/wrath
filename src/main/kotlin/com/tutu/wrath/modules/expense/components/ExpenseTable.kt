package com.tutu.wrath.modules.expense.components

import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.datatable.DataTable
import com.tutu.wrath.anger.tables.datatable.dataTable
import com.tutu.wrath.modules.expense.model.Expense
import com.tutu.wrath.util.unwrap
import io.kvision.core.Container
import io.kvision.html.Div
import io.kvision.snabbdom.VNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias GetExpenses = suspend () -> Result<List<Expense>>

class ExpenseTable(private val getExpenses: GetExpenses) : Div(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private var table: DataTable? = null

    init {
        table = dataTable(
            columns = {
                column("name", "Nome")
                column("amount", "Valor")
                column("date", "Data")
                column("payed", "Pago")
                column("card.name", "Cartão")
                column("beneficiary.name", "Beneficiario")
                column("installments", "Parcelamento")
                column("actions", "Ações", shouldFilter = false)
            }
        )
    }

    override fun afterInsert(node: VNode) {
        //setHooks()
        initialize()
    }

    //private fun setHooks() {
    //    incomes.subscribe { incomes -> rows.setState(incomes.map { Row(it) }) }
    //}
    //

    private fun initialize() {
        table?.changeState() {
            launch {
                rows = getExpenses().unwrap(emptyList()).map { Row(it) }
            }
        }
    }

}

fun Container.expenseTable(getExpenses: GetExpenses) : ExpenseTable {
    return ExpenseTable(getExpenses).also {
        add(it)
    }
}

