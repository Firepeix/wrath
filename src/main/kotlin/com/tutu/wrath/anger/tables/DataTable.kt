package com.tutu.wrath.anger.tables

import com.tutu.wrath.anger.pop.DropdownScope
import com.tutu.wrath.anger.pop.dropdown
import com.tutu.wrath.anger.tables.Table.Companion.table
import com.tutu.wrath.util.State
import com.tutu.wrath.util.StateProperties
import com.tutu.wrath.util.observable
import io.kvision.core.Container
import io.kvision.core.onClick
import io.kvision.html.Div
import io.kvision.html.div
import io.kvision.html.th

typealias OnRowChanged = (List<Row>) -> Unit

data class DataTable(val component: DataTableComponent, val properties: Properties) {
    data class Listeners(var onRowChanged: OnRowChanged?)

    data class Attributes(val header: String? = null)

    class Properties(vararg var columns: Column, rows: List<Row> = emptyList(), isLoading: Boolean = false): StateProperties<Listeners> {
        override val listeners = Listeners(null)

        var rows by observable(rows) { listeners.onRowChanged?.invoke(it) }
    }
}


class DataTableComponent(properties: DataTable.Properties, private val attributes: DataTable.Attributes) : Div() {
    private val state = State(properties) {
        onRowChanged = ::onRowChanged
    }

    private var table: TableScope? = null

    init {
        table = table(
            properties = Table.Properties(*properties.columns, rows = properties.rows),
            attributes = Table.Attributes(header = attributes.header, makeHeader = makeDataHeader())
        )
    }

    private fun onRowChanged(value: List<Row>) {
        table?.change {
            rows = value
        }
    }

    private fun makeDataHeader(): HeaderMaker {
        return {index: Int, column: Column ->
            var dropdown: DropdownScope? = null
            th(className = "px-3 py-4 dark:border-neutral-500") {
                onClick {
                    dropdown?.toggle()
                }

                setAttribute("colspan", column.colspan.toString())
                if (column.isSubdivided) {
                    addCssClass("text-center")
                    if (column.isNextSection(index)) addCssClass("border-l")
                }

                div(column.label)

                dropdown = dropdown()
            }
        }
    }
}

fun Container.dataTable(properties: DataTable.Properties, attributes: DataTable.Attributes = DataTable.Attributes()) : DataTable {
    val component = DataTableComponent(properties, attributes).also { add(it) }
    return DataTable(component, properties)
}
