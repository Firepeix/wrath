package com.tutu.wrath.anger.form.select

import com.tutu.wrath.anger.layout.attributes
import io.kvision.core.ClassSetBuilder
import io.kvision.html.Div
import io.kvision.html.label

internal class InnerSelect(isLoading: Boolean, label: String?, private val header: Boolean, init: InnerSelect.() -> Unit): Div(className = "xl:w-100") {
    var isLoading by refreshOnUpdate(isLoading)

    init {
        init.invoke(this)
        if (label != null) {
            label(label) { attributes("data-te-select-label-ref" to "") }
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (header) classSetBuilder.add("header-mode")
        if (isLoading) classSetBuilder.add("hidden")
    }
}

internal fun <T: SelectItem> SelectComponent<T>.innerSelect(isLoading: Boolean, label: String?, header: Boolean, init: (InnerSelect) -> Unit): InnerSelect {
    return InnerSelect(isLoading, label, header, init).also {
        add(it)
    }
}