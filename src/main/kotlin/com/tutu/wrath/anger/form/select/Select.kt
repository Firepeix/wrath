package com.tutu.wrath.anger.form.select

import com.tutu.wrath.anger.layout.attributes
import com.tutu.wrath.anger.modules.TailwindElementsModule
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.core.onEvent
import io.kvision.form.select.SelectInput
import io.kvision.form.select.selectInput
import io.kvision.html.Div
import io.kvision.html.div
import io.kvision.html.label
import io.kvision.snabbdom.VNode
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.utils.event

class Select<T: SelectItem>(
    private val value: ObservableValue<T?>,
    private val options: ObservableValue<List<T>>,
    private val asHeader: Boolean = false,
    private val label: String? = null,
    private val startInit: Boolean = true
) : Div(className = "justify-center w-full") {

    private val optionsMap: ObservableValue<List<StringPair>> = ObservableValue(createMap(options.value))
    private var select: SelectInput? = null
    private var isInitialized = false

    init {
        if (!startInit) addCssClass("delay-init")
        val self = this
        div(className = "xl:w-100") {
            if (self.asHeader) addCssClass("header-mode")
            self.select = selectInput(options = self.optionsMap.value, className = "w-full") {
                onEvent {
                    event("optionSelect.te.select") { self.onChange() }
                }
            }.bind(self.optionsMap) { options = it }

            if (self.label != null) {
                label(self.label) { attributes("data-te-select-label-ref" to "") }
            }
        }
    }

    override fun afterInsert(node: VNode) {
        if (startInit) initialize()
        setHooks()
    }

    fun initialize() {
        if (!isInitialized) {
            select?.getElement()?.let { element ->
                TailwindElementsModule.createSelect(element)
                value.value?.let { setValue(it.toSelectItem().first) }
            }
            isInitialized = true
        }
    }

    private fun setHooks() {
        options.subscribe { options -> optionsMap.setState(createMap(options))}
    }

    private fun createMap(options: List<SelectItem>): List<StringPair> {
        return options.map { it.toSelectItem() }
    }

    private fun onChange() {
        val value = getValue()
        this.value.setState(options.value.firstOrNull { it.toSelectItem().first == value })
    }

    private fun getValue() =  select?.getElement()?.run { TailwindElementsModule.getValue(this) }

    private fun setValue(value: String) = select?.getElement()?.run { TailwindElementsModule.setValue(value, this) }
}

fun <T: SelectItem> Container.select(
    value: ObservableValue<T?>,
    options: ObservableValue<List<T>>,
    label: String? = null,
    asHeader: Boolean = false,
    startInit: Boolean = true
)
: Select<T> {
    val component = Select(value, options, asHeader, label, startInit)
    add(component)
    return component
}

fun <T: SelectItem> Container.select(
    value: ObservableValue<T?>,
    options: List<T>,
    label: String? = null,
    asHeader: Boolean = false,
    startInit: Boolean = true
): Select<T> {
    return select(value, ObservableValue(options), label, asHeader, startInit)
}
