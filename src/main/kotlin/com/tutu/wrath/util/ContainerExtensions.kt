package com.tutu.wrath.util

import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.form.select.SelectInput
import io.kvision.html.Div
import io.kvision.html.Table
import io.kvision.html.Tbody
import io.kvision.state.ObservableValue
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

typealias VModel<T> = ObservableValue<T>

fun <T> model(value: T, onChange: (newValue: T) -> Unit): VModel<T> {
    return VModel(value).also { it.subscribe(onChange) }
}

inline fun <T> observable(initialValue: T, crossinline onChange: (newValue: T) -> Unit): ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(initialValue) {
  override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = onChange(newValue)
}


fun Container.toggleClass(css: String) {
    if (hasCssClass(css)) {
        removeCssClass(css)
    } else {
        addCssClass(css)
    }
}


fun Container.div(className: String? = null, init: (Div) -> Unit): Div {
    return Div(className = className, init = init).also {
        add(it)
    }
}

fun Container.selectInput(options: List<StringPair>? = null, className: String? = null, parent: Container? = null, init: (SelectInput) -> Unit): SelectInput {
    return SelectInput(options, className = className, init = init).also {
        if (parent == null) add(it)
        else parent.add(it)
    }
}

fun Container.baseTable(className: String? = null,init: (Table) -> Unit): Table {
    return Table(className = className, init = init).also {
        add(it)
    }
}

fun Container.tBody(className: String? = null, init: (Tbody) -> Unit): Tbody {
    return Tbody(className, init).also {
        add(it)
    }
}

abstract class Statefull<A, S>(protected val component: A, protected val properties: S) {
    fun change(scope: S.(A) -> Unit) {
        scope.invoke(properties, component)
    }
}


interface ContainerStyle

fun <T: ContainerStyle> T.className(): String {
    return toString().replace(".*\\(".toRegex(), "").replace("\\).*".toRegex(), "")
}

interface ContainerAttributes {
    fun all(): Array<StringPair>
}