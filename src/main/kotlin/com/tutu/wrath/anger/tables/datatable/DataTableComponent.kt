package com.tutu.wrath.anger.tables.datatable

import com.tutu.wrath.anger.pop.dropdown
import com.tutu.wrath.anger.tables.*
import com.tutu.wrath.anger.tables.Table.Companion.table
import com.tutu.wrath.irate.table.ThExtension.Companion.th
import com.tutu.wrath.util.style
import io.kvision.core.StringPair
import io.kvision.core.onClick
import io.kvision.html.Div
import io.kvision.html.span

class DataTableComponent(state: DataTable.State, val header: String? = null) : Div() {
    private val listeners = state.init {
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
        return {index: Int, column: Column ->
            th(className = Style.th(column, index), attributes = Attributes.th(column)) {
                val label = span(column.label, className = Style.thLabel(column).className())

                if (column.shouldFilter) {
                    val dropdown = dropdown()

                    label.onClick {
                        dropdown.toggle()
                    }
                }
            }
        }
    }

    internal object Style {
        fun th(column: Column, index: Int) = style {
            "text-center" where column.isSubdivided
            "border-l" where (column.isSubdivided && column.isNextSection(index))
            default("px-3 py-4 dark:border-neutral-500")
        }

        fun thLabel(column: Column) = style {
            "cursor-pointer" where column.shouldFilter
        }
    }

    internal object Attributes {
        fun th(column: Column): Array<StringPair> = arrayOf(
            "colspan" to "${column.colspan}"
        )
    }
}