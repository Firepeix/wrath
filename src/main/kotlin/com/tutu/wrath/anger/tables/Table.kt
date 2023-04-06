package com.tutu.wrath.anger.tables

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.util.*
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.html.div
import io.kvision.state.ObservableValue
import io.kvision.state.bind

data class Table(val component: TableComponent, val properties: Properties) {
    data class Attributes(val header: String? = null, val showColumns: Boolean = true, val flat: Boolean = false, val estimatedRows: Int = 5)
    class Properties(vararg columns: Column, rows: List<Row>, footer: Display? = null, isLoading: Boolean = false): StateProperties<Listeners> {
        override val listeners = Listeners()

        var columns by observable(columns) { listeners.onColumnsChanged?.invoke(it.toList()) }
        var rows by observable(rows) { listeners.onRowsChanged?.invoke(it) }
        var footer by observable(footer) { listeners.onFooterChanged?.invoke(it) }
        var isLoading by observable(isLoading) { listeners.onLoadingChanged?.invoke(it) }
    }

    class Listeners {
        var onColumnsChanged: ((List<Column>) -> Unit)? = null
        var onRowsChanged: ((List<Row>) -> Unit)? = null
        var onFooterChanged: ((Display?) -> Unit)? = null
        var onLoadingChanged: ((Boolean) -> Unit)? = null
    }
}


class TableComponent(properties: Table.Properties, private val attributes: Table.Attributes) : Div() {

    private val state = State(properties) {
        onRowsChanged = ::onRowsChanged
        onFooterChanged = ::onFooterChanged
        onLoadingChanged = ::onLoadingChanged
    }

    private val footer = VModel(properties.footer)
    private val rows = VModel(properties.rows)
    private var isLoading: Boolean = properties.isLoading

    init {
        attributes.header?.let { tableHeader(it) }
        div(className = "flex flex-col overflow-x-auto dark:text-neutral-100") { container ->
            container.div(className = "inline-block min-w-full")  { div ->
                div.baseTable(className = "min-w-full text-center text-sm font-light dark:border-neutral-500 dark:border-none") {
                    if (attributes.header != null) addCssClass("border-t")
                    if (attributes.showColumns) {
                        it.thead(className = "font-medium dark:border-neutral-500") {
                            tr(className = "border-b transition duration-300 ease-in-out hover:bg-neutral-100 dark:border-neutral-500 dark:hover:bg-neutral-600") {
                                properties.columns.forEachIndexed { index, column ->
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

                    it.tBody { body ->
                        body.bind(rows) {rows ->
                            rows.forEach { row ->
                                body.tr(className = "content border-b transition duration-300 ease-in-out hover:bg-neutral-100 dark:border-neutral-500 dark:hover:bg-neutral-600") {
                                    if (row.shouldHighlight()) highlight()
                                    properties.columns.forEachIndexed { index, column ->
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

                            for (index in 1 .. this@TableComponent.attributes.estimatedRows) {
                                body.tr(className = "border-b transition duration-300 ease-in-out hover:bg-neutral-100 dark:border-neutral-500 dark:hover:bg-neutral-600 loading") {
                                    properties.columns.forEachIndexed { index, column ->
                                        for (i in 1 .. column.colspan) {
                                            td(className = "whitespace-nowrap px-3 py-4 font-medium dark:border-neutral-500") {
                                                p(className = "animate-pulse w-full") {
                                                    span(className = "inline-block min-h-[1em] w-full rounded flex-auto cursor-wait bg-current align-middle text-base text-neutral-700 opacity-50 dark:text-neutral-50")
                                                }
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

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (!attributes.flat) {
            classSetBuilder.add("shadow-md-strong")
        }

        if (!isLoading) classSetBuilder.add("loaded") else classSetBuilder.add("loading")
    }

    private fun onRowsChanged(value: List<Row>) {
        rows.setState(value)
    }

    private fun onFooterChanged(value: Display?) {
        footer.setState(value)
    }

    private fun onLoadingChanged(value: Boolean) {
        isLoading = value
        super.refresh()
    }
}


fun Container.table(properties: Table.Properties, attributes: Table.Attributes = Table.Attributes()) : Table {
    val component = TableComponent(properties, attributes).also { add(it) }
    return Table(component, properties)
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
                    it.thead(className = "font-medium dark:border-neutral-500") {
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

                it.tbody {
                    bind(rows) { rows ->
                        rows.forEach { row ->
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