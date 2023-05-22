package com.tutu.wrath.anger.tables.datatable

import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.ColumnMaker
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.util.*
import io.kvision.core.Container

typealias OnRowChanged = (List<Row>) -> Unit

fun Container.dataTable(columns: ColumnMaker.() -> Column, header: String? = null) : DataTable {
    val maker = ColumnMaker().also { columns(it) }
    val state = DataTable.State(maker.columns, emptyList())
    val component = DataTableComponent(state, header).also { add(it) }
    return DataTable(state, component)
}

class DataTable internal constructor(override val state: State, private val component: DataTableComponent): StatefullWidget<DataTable.Scope>() {
    class Listeners {
        var onRowChanged: OnRowChanged? = null
    }

    abstract class Scope : StateScope<Listeners>() {
        abstract var rows: List<Row>
    }

    class State(var columns: List<Column>, rows: List<Row>): Scope() {
        override val listeners = Listeners()

        override var rows by observable(rows) { listeners.onRowChanged?.invoke(it) }
    }
}




