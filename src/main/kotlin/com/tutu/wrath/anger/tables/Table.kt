package com.tutu.wrath.anger.tables

import io.kvision.core.Container
import io.kvision.table.cell
import io.kvision.table.row
import io.kvision.table.table as baseTable

fun Container.table(columns: List<Column>, rows: List<Row>) {
    baseTable(columns.map { it.label }) {
        rows.forEach { row ->
            row {
                columns.forEach { column ->
                    cell(row.getRowValue(column.id))
                }
            }
        }
    }
}