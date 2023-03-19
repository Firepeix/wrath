package com.tutu.wrath.anger.tables

import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.tbody
import io.kvision.html.td
import io.kvision.html.th
import io.kvision.html.thead
import io.kvision.html.tr
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.html.table as baseTable

fun Container.table(columns: List<Column>, rows: ObservableValue<List<Row>>, header: String? = null) {
    header?.let { tableHeader(it) }

    baseTable(className = "w-full table") {
        thead {
            tr {
                columns.forEach {
                    th(it.label) {
                        setAttribute("colspan", it.colspan.toString())
                    }
                }
            }
        }

        tbody {
            bind(rows) {
                it.forEach {row ->
                    tr {
                        columns.forEach { column ->
                            for (i in 1 .. column.colspan) {
                                val display = row.getRowValue(column.id, i - 1)
                                td(display.content) {
                                    fontWeight = display.weight
                                    color = display.fontColor
                                    fontSize = display.size
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Container.tableHeader(header: String) {
    div(header, className = "w-full bg-neutral p-4 text-center font-bold rounded-t")
}