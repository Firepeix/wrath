package com.tutu.wrath.anger.tables

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.util.toggleClass
import io.kvision.core.Container
import io.kvision.core.TextAlign
import io.kvision.html.*
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.html.table as baseTable


fun Div.table(columns: List<Column>, rows: ObservableValue<List<Row>>, header: String? = null, footer: Display? = null) {
    table(columns, rows, header, ObservableValue(footer))
}

fun Div.table(columns: List<Column>, rows: ObservableValue<List<Row>>, header: String? = null, footer: ObservableValue<Display?>) {
    addCssClass("shadow-md-strong")
    header?.let { tableHeader(it) }

    div(className = "flex flex-col overflow-x-auto dark:text-neutral-100") {
        div(className = "inline-block min-w-full") {
            baseTable(className = "min-w-full text-center text-sm font-light dark:border-neutral-500 border-t dark:border-none") {
                thead(className = "font-medium dark:border-neutral-500") {
                    tr(className = "border-b transition duration-300 ease-in-out hover:bg-neutral-100 dark:border-neutral-500 dark:hover:bg-neutral-600") {
                        columns.forEachIndexed { index, column ->
                            th(column.label, className = "px-3 py-4 dark:border-neutral-500") {
                                setAttribute("colspan", column.colspan.toString())
                                if (column.isSubdivided) {
                                    addCssClass("text-center")
                                    if (column.isNextSection(index)) addCssClass("border-l")
                                }
                            }
                        }
                    }
                }

                tbody {
                    bind(rows) {
                        it.forEach { row ->
                            tr(className = "border-b transition duration-300 ease-in-out hover:bg-neutral-100 dark:border-neutral-500 dark:hover:bg-neutral-600") {
                                if (row.shouldHighlight()) highlight()

                                columns.forEachIndexed { index, column ->
                                    for (i in 1 .. column.colspan) {
                                        val display = row.getRowValue(column.id, i - 1)
                                        td(display.content, className = "whitespace-nowrap px-3 py-4 font-medium dark:border-neutral-500") {
                                            fontWeight = display.weight
                                            display.fontColor?.let { display -> color = display }
                                            fontSize = display.size
                                            if (column.isNextSection(index, i - 1)) addCssClass("border-l")
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

    footer(footer)
}

internal fun Container.tableHeader(header: String) {
    div(header, className = "w-full bg-neutral p-3 text-center font-bold rounded-t dark:text-neutral-100 dark:bg-neutral-700")
}

internal fun Container.footer(display: ObservableValue<Display?>) {
    div(className = "w-full bg-neutral p-3 text-center dark:bg-neutral-700").bind(display) { it?.let { display ->
        content = display.content
        fontWeight = display.weight
        display.fontColor?.let { fontColor -> color = fontColor }
        fontSize = display.size
    } }
}

internal fun Tr.highlight() {
    addCssClass("cursor-pointer")
    setEventListener<Tr> {
        click = {
            toggleClass("hover:bg-neutral-100")
            toggleClass("dark:border-neutral-500")
            toggleClass("dark:hover:bg-neutral-600")

            toggleClass("bg-info-300")
            toggleClass("hover:bg-info-400")
            toggleClass("dark:bg-info")
            toggleClass("dark:border-info-500")
            toggleClass("dark:hover:bg-info-600")
        }
    }
}