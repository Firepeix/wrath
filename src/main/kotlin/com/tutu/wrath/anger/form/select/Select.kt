package com.tutu.wrath.anger.form.select

import com.tutu.wrath.anger.layout.attributes
import com.tutu.wrath.anger.modules.TailwindElementsModule
import com.tutu.wrath.anger.skeleton.input.SelectSkeleton
import com.tutu.wrath.util.VModel
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.core.onEvent
import io.kvision.form.select.SelectInput
import io.kvision.html.Div
import io.kvision.html.label
import io.kvision.snabbdom.VNode
import io.kvision.state.bind
import io.kvision.utils.event

private class InnerSelect(
    input: SelectInput,
    isLoading: Boolean,
    label: String?,
    private val header: Boolean,
): Div(className = "xl:w-100") {

    var isLoading by refreshOnUpdate(isLoading)
    init {
        add(input)

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

class Select<T: SelectItem>(
    private val value: VModel<T?>,
    options: List<T>,
    header: Boolean = false,
    label: String? = null,
    private val lateInit: Boolean = false,
    isLoading: Boolean = false
) : Div(className = "justify-center w-full") {

    var isLoading by refreshOnUpdate(isLoading) {
        super.refresh()
        skeleton.isLoading = it
        innerSelect.isLoading = it
    }

    private val optionMap = VModel(createMap(options))

    var options by refreshOnUpdate(options) {
        super.refresh()
        optionMap.setState(createMap(it))
    }


    private var isInitialized = false

    private val skeleton = SelectSkeleton(isLoading)

    private val input = SelectInput(options = optionMap.value, className = "w-full") {
        onEvent {
            event("optionSelect.te.select") { onChange() }
        }
    }.bind(optionMap) { this.options = it }

    private val innerSelect = InnerSelect(input, isLoading, label, header)

    init {
        add(skeleton)
        add(innerSelect)
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (lateInit) classSetBuilder.add("delay-init")
    }

    override fun afterInsert(node: VNode) {
        if (!lateInit) initialize()
    }

    fun initialize() {
        if (!isInitialized) {
            input.getElement()?.let { TailwindElementsModule.createSelect(value.value?.toSelectItem()?.first, it) }
            value.subscribe {  it?.let { setValue(it.toSelectItem().first) } }
            isInitialized = true
        }
    }

    private fun onChange() {
        val value = getValue()
        this.value.setState(options.firstOrNull { it.toSelectItem().first == value })
    }

    private fun getValue() =  input.getElement()?.run { TailwindElementsModule.getValue(this) }

    private fun setValue(value: String) = input.getElement()?.run { TailwindElementsModule.setValue(value, this) }
}

fun <T: SelectItem> Container.select(
    value: VModel<T?>,
    options: List<T>,
    label: String? = null,
    header: Boolean = false,
    lateInit: Boolean = false
): Select<T> {
    return Select(value, options, header, label, lateInit).also { add(it) }
}

internal fun createMap(options: List<SelectItem>): List<StringPair> {
    return options.map { it.toSelectItem() }
}