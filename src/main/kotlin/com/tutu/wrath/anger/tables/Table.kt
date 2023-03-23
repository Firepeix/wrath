package com.tutu.wrath.anger.tables

import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.html.table as baseTable

fun Div.table(columns: List<Column>, rows: ObservableValue<List<Row>>, header: String? = null) {
    addCssClass("shadow-md-strong")
    header?.let { tableHeader(it) }

    div(className = "flex flex-col overflow-x-auto dark:text-neutral-100") {
        div(className = "inline-block min-w-full py-2 dark:py-0") {
            baseTable(className = "min-w-full text-center text-sm font-light dark:border-neutral-500 border-t dark:border-none") {
                thead(className = "font-medium dark:border-neutral-500") {
                    tr(className = "border-b transition duration-300 ease-in-out hover:bg-neutral-100 dark:border-neutral-500 dark:hover:bg-neutral-600") {
                        columns.forEach {
                            th(it.label, className = "px-3 py-4 dark:border-neutral-500") {
                                setAttribute("colspan", it.colspan.toString())
                            }
                        }
                    }
                }

                tbody {
                    bind(rows) {
                        it.forEach {row ->
                            tr(className = "border-b transition duration-300 ease-in-out hover:bg-neutral-100 dark:border-neutral-500 dark:hover:bg-neutral-600") {
                                columns.forEach { column ->
                                    for (i in 1 .. column.colspan) {
                                        val display = row.getRowValue(column.id, i - 1)
                                        td(display.content, className = "whitespace-nowrap px-3 py-4 font-medium dark:border-neutral-500") {
                                            fontWeight = display.weight
                                            display.fontColor?.let { display -> color = display }
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
    }
}

fun Container.tableHeader(header: String) {
    div(header, className = "w-full bg-neutral p-4 text-center font-bold rounded-t dark:text-neutral-100 dark:bg-neutral-700")
}