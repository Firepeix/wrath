package com.tutu.wrath.anger.tables

import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.table.cell
import io.kvision.table.row
import io.kvision.table.table as baseTable

fun Container.table(columns: List<Column>, rows: ObservableValue<List<Row>>, header: String? = null) {
    header?.let { tableHeader(it) }

    baseTable(columns.map { it.label }, className = "w-full") {
        bind(rows) {
            it.forEach { row ->
                row(className = "hover") {
                    columns.forEach { column ->
                        cell(row.getRowValue(column.id))
                    }
                }
            }
        }
    }
}

fun Container.tableHeader(header: String) {
    div(header, className = "w-full bg-neutral p-4 text-center font-bold rounded-t")
}