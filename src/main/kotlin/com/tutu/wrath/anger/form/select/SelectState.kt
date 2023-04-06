package com.tutu.wrath.anger.form.select

import com.tutu.wrath.util.VModel

class SelectState<T: SelectItem>(private val value: VModel<T?>, private val properties: Select.Properties<T>, init: Listeners<T>.(SelectState<T>) -> Unit,) {
    class Listeners<T: SelectItem> {
        var onValueChanged: ((SelectItem?) -> Unit)? = null
        var onLoadingChanged: ((Boolean) -> Unit)? = null
        var onOptionsChanged: ((List<T>) -> Unit)? = null
    }


    init {
        init.invoke(properties.listeners, this)
        value.subscribe { properties.listeners.onValueChanged?.invoke(it) }
    }

    fun getOption(id: String?): T? {
        if (id == null) return null
        return properties.options.firstOrNull { it.toSelectItem().first == id }
    }
}

