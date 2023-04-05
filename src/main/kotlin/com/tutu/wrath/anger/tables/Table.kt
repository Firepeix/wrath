package com.tutu.wrath.anger.tables

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.util.toggleClass
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.html.table as baseTable

class Table(
    columns: List<Column>,
    rows: List<Row>,
    header: String? = null,
    footer: Display? = null,
    private val flat: Boolean = false,
    showColumns: Boolean = true
) : Div() {
    private val innerFooter = ObservableValue(footer)
    private val innerRows = ObservableValue(rows)

    var footer by refreshOnUpdate(footer) {
        super.refresh()
        innerFooter.setState(it)
    }

    var rows by refreshOnUpdate(rows) {
        super.refresh()
        innerRows.setState(it)
    }

    init {
        val self = this
        header?.let { tableHeader(it) }

        div(className = "flex flex-col overflow-x-auto dark:text-neutral-100") {
            div(className = "inline-block min-w-full") {
                baseTable(className = "min-w-full text-center text-sm font-light dark:border-neutral-500 dark:border-none") {
                    if (header != null) addCssClass("border-t")
                    if (showColumns) {
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
                    }

                    tbody {
                        bind(self.innerRows) {
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

        footer(innerFooter)
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (!flat) {
            classSetBuilder.add("shadow-md-strong")
        }
    }
}


fun Container.table(
    columns: List<Column>,
    rows: List<Row>,
    header: String? = null,
    footer: Display? = null,
    flat: Boolean = false,
    showColumns: Boolean = true
) : Table {
    return Table(columns, rows, header, footer, flat, showColumns).also { add(it) }
}

fun Div.tableDeprecated(
    columns: List<Column>,
    rows: ObservableValue<List<Row>>,
    header: String? = null,
    footer: Display? = null,
    flat: Boolean = false,
    showColumns: Boolean = true
) {
    tableDeprecated(columns, rows, header, ObservableValue(footer), flat, showColumns)
}

fun Div.tableDeprecated(
    columns: List<Column>,
    rows: ObservableValue<List<Row>>,
    header: String? = null,
    footer: ObservableValue<Display?>,
    flat: Boolean = false,
    showColumns: Boolean = true
) {
    if (!flat) addCssClass("shadow-md-strong")
    header?.let { tableHeader(it) }

    div(className = "flex flex-col overflow-x-auto dark:text-neutral-100") {
        div(className = "inline-block min-w-full") {
            baseTable(className = "min-w-full text-center text-sm font-light dark:border-neutral-500 dark:border-none") {
                if (header != null) addCssClass("border-t")
                if (showColumns) {
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