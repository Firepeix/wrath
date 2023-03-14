package com.tutu.wrath.anger.tables

import io.kvision.core.Background
import io.kvision.core.Color
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.FontWeight
import io.kvision.core.UNIT
import io.kvision.html.div
import io.kvision.table.cell
import io.kvision.table.row
import io.kvision.table.table as baseTable

fun Container.table(columns: List<Column>, rows: List<Row>, header: String? = null) {
    header?.let { tableHeader(it) }

    baseTable(columns.map { it.label }, className = "w-full") {
        rows.forEach { row ->
            row(className = "hover") {
                columns.forEach { column ->
                    cell(row.getRowValue(column.id))
                }
            }
        }
    }
}

fun Container.tableHeader(header: String) {
    div(header, className = "w-full bg-neutral p-4 text-center font-bold rounded-t") {
        fontWeight = FontWeight.BOLD
        background = Background(Color("hsl(var(--n))"))
        borderRadiusList = listOf(CssSize(4, UNIT.px), CssSize(4, UNIT.px), CssSize(0, UNIT.px), CssSize(0, UNIT.px))

    }
}