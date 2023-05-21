package com.tutu.wrath.anger.tables

import com.tutu.wrath.anger.pop.dropdown
import com.tutu.wrath.anger.tables.Table.Companion.table
import com.tutu.wrath.irate.table.ThExtension.Companion.th
import com.tutu.wrath.util.*
import io.kvision.core.Container
import io.kvision.core.onClick
import io.kvision.html.Div
import io.kvision.html.span

typealias OnRowChanged = (List<Row>) -> Unit

fun Container.dataTable(columns: ColumnMaker.() -> Column, header: String? = null) : DataTable {
    val maker = ColumnMaker().also { columns(it) }
    val state = DataTable.State(maker.columns, emptyList())
    val component = DataTableComponent(state, header).also { add(it) }
    return DataTable(state, component)
}

data class DataTable(override val state: State, private val component: DataTableComponent): StatefullWidget<DataTable.Scope>() {
    abstract class Scope : StateScope {
        abstract var rows: List<Row>
        var onRowChanged: OnRowChanged? = null
    }

    class State(var columns: List<Column>, rows: List<Row>): Scope() {
        override var rows by observable(rows) { onRowChanged?.invoke(it) }
    }
}


class DataTableComponent(state: DataTable.State, val header: String? = null) : Div() {
    private val style = DataTableStyle()
    private val attr = DataTableAttributes()

    private val _initialState = state.init {
        onRowChanged = ::onRowChanged
    }

    private var table: TableScope? = null

    init {
        table = table(
            properties = Table.Properties(*state.columns.toTypedArray(), rows = state.rows),
            attributes = Table.Attributes(header = header, makeHeader = makeDataHeader())
        )
    }

    private fun onRowChanged(value: List<Row>) {
        table?.change {
            rows = value
        }
    }

    private fun makeDataHeader(): HeaderMaker {
        val style = this.style
        val attributes = this.attr
        return {index: Int, column: Column ->
            th(className = style.th(column, index), attributes = attributes.th(column)) {
                val label = span(column.label, className = style.thLabel(column).className())

                if (column.shouldFilter) {
                    val dropdown = dropdown()

                    label.onClick {
                        dropdown.toggle()
                    }
                }
            }
        }
    }
}

private class DataTableStyle {
    class ColumnHeaderStyle internal constructor(column: Column, index: Int): ContainerStyle {
        private val subDivided = if (column.isSubdivided) "text-center" else ""
        private val leftBorder = if (column.isSubdivided && column.isNextSection(index)) "border-l" else ""

        override fun toString() = "px-3 py-4 dark:border-neutral-500 $subDivided $leftBorder"

        class LabelStyle internal constructor(column: Column): ContainerStyle {
            private val canClick = if (column.shouldFilter) "cursor-pointer" else ""
            override fun toString() = canClick
        }
    }



    fun th(column: Column, index: Int) = ColumnHeaderStyle(column, index)
    fun thLabel(column: Column) = ColumnHeaderStyle.LabelStyle(column)
}

private class DataTableAttributes {
    class ColumnHeaderAttributes internal constructor(private val column: Column): ContainerAttributes {
        override fun all() = arrayOf(
            "colspan" to "${column.colspan}"
        )

    }

    fun th(column: Column) = ColumnHeaderAttributes(column)

}