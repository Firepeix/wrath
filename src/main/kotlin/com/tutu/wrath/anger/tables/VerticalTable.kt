package com.tutu.wrath.anger.tables

import io.kvision.core.Container
import io.kvision.html.tbody
import io.kvision.html.td
import io.kvision.html.th
import io.kvision.html.thead
import io.kvision.html.tr
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.html.table as baseTable

fun Container.verticalTable(columns: List<Column>, rows: ObservableValue<List<Row>>) {
    baseTable(className = "w-full table") {
        thead {
            tr {
                columns.forEach {
                    th(it.label) {
                        setAttribute("colspan", "2")
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
