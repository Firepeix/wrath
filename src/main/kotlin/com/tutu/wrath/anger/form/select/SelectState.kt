package com.tutu.wrath.anger.form.select

import com.tutu.wrath.util.VModel
import com.tutu.wrath.util.observable

class SelectProperties<T: SelectItem>(options: List<T>, isLoading: Boolean = false) {
    var listeners: SelectState.Listeners<T>? = null
    var isLoading by observable(isLoading) { listeners?.onLoadingChanged?.invoke(it) }
    var options by observable(options) { listeners?.onOptionsChanged?.invoke(it) }
}

class SelectState<T: SelectItem> private constructor(
    private val value: VModel<T?>,
    private val properties: SelectProperties<T>,
    private val listeners: Listeners<T>,
    init: Listeners<T>.(SelectState<T>) -> Unit,
) {

    constructor(value: VModel<T?>, properties: SelectProperties<T>, init: Listeners<T>.(SelectState<T>) -> Unit): this(value, properties, Listeners<T>(), init)

    class Listeners<T: SelectItem> {
        var onValueChanged: ((SelectItem?) -> Unit)? = null
        var onLoadingChanged: ((Boolean) -> Unit)? = null
        var onOptionsChanged: ((List<T>) -> Unit)? = null
    }


    init {
        init.invoke(listeners, this)
        properties.listeners = listeners
        value.subscribe { listeners.onValueChanged?.invoke(it) }
    }

    fun getOption(id: String?): T? {
        if (id == null) return null
        return properties.options.firstOrNull { it.toSelectItem().first == id }
    }
}

