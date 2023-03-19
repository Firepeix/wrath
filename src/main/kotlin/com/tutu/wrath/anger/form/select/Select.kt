package com.tutu.wrath.anger.form.select

import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.form.select.TomSelectCallbacks
import io.kvision.form.select.tomSelect
import io.kvision.state.ObservableValue
import io.kvision.state.bind

val onChange = { value: Any?, state: ObservableValue<String?> ->
    state.setState(value?.toString())
}

fun Container.select(value: ObservableValue<String?>, options: ObservableValue<List<StringPair>>, asHeader: Boolean = false) {
    tomSelect(options = options.value, tsCallbacks = TomSelectCallbacks(onChange = { onChange(it, value) })) {
        if (asHeader)  addCssClass("header-mode")
    }.bind(options) {
        this.options = it
        placeholder = it.firstOrNull()?.second ?: ""
    }
}
