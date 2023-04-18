package com.tutu.wrath.anger.form.select

import com.tutu.wrath.anger.form.select.Select.Attributes
import com.tutu.wrath.anger.form.select.Select.Properties
import com.tutu.wrath.anger.modules.TailwindElementsModule
import com.tutu.wrath.anger.modules.TailwindElementsModule.getValue
import com.tutu.wrath.anger.skeleton.input.SelectSkeleton
import com.tutu.wrath.anger.skeleton.input.selectSkeleton
import com.tutu.wrath.util.Statefull
import com.tutu.wrath.util.VModel
import com.tutu.wrath.util.observable
import com.tutu.wrath.util.selectInput
import io.kvision.core.ClassSetBuilder
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.core.onEvent
import io.kvision.form.select.SelectInput
import io.kvision.html.Div
import io.kvision.snabbdom.VNode
import io.kvision.state.bind
import io.kvision.utils.event

interface SelectScope<T: SelectItem> {
    var isLoading: Boolean
    var options: List<T>
}

interface SelectActions<T: SelectItem> {
    fun initialize()
}

data class Select<T: SelectItem>(private val component: SelectComponent<T>, private val properties: Properties<T>) {
    data class Attributes(val header: Boolean = false, val label: String? = null, val lateInit: Boolean = false)
    class Properties<T: SelectItem>(options: List<T>, isLoading: Boolean = false) : SelectScope<T> {
        val listeners = SelectState.Listeners<T>()
        override var isLoading by observable(isLoading) { listeners.onLoadingChanged?.invoke(it) }
        override var options by observable(options) { listeners.onOptionsChanged?.invoke(it) }
    }

    fun change(scope: SelectScope<T>.(actions: SelectActions<T>) -> Unit) {
        scope.invoke(properties, component)
    }
}

class SelectComponent<T: SelectItem>(value: VModel<T?>, properties: Properties<T>, private val attributes: Attributes) : Div(className = "justify-center w-full"), Statefull<SelectState<T>>, SelectActions<T> {
    private val state = SelectState(value, properties) {
        onValueChanged = ::onValueChanged
        onOptionsChanged = ::onOptionsChanged
        onLoadingChanged = ::onLoadingChanged
    }

    private val optionMap = VModel(createMap(properties.options))
    private var isInitialized = false
    private val initialValue = value.value
    private var skeleton: SelectSkeleton? = null
    private var select : InnerSelect? = null
    private var input: SelectInput? = null


    init {
        skeleton = selectSkeleton(properties.isLoading)
        select = innerSelect(properties.isLoading, attributes.label, attributes.header) {
            input = selectInput(optionMap.value, "w-full", parent = it) {
                onEvent {
                    event("optionSelect.te.select") { onChange(value) }
                }
            }.bind(optionMap) { options ->
                this.options = options
            }
        }
    }

    override fun buildClassSet(classSetBuilder: ClassSetBuilder) {
        super.buildClassSet(classSetBuilder)
        if (attributes.lateInit) classSetBuilder.add("delay-init")
    }

    override fun afterInsert(node: VNode) {
        if (!attributes.lateInit) initialize()
    }

    override fun initialize() {
        if (!isInitialized) {
            input?.getElement()?.let { TailwindElementsModule.createSelect(initialValue?.toSelectItem()?.first, it) }
            isInitialized = true
        }
    }

    private fun onChange(value: VModel<T?>) {
        val selectValue = input?.getElement()?.run { getValue(this) }
        value.setState(state.getOption(selectValue))
    }

    private fun setValue(value: String) = input?.getElement()?.run { TailwindElementsModule.setValue(value, this) }

    private fun onLoadingChanged(value: Boolean) {
        skeleton?.isLoading = value
        select?.isLoading = value
    }

    private fun onValueChanged(value: SelectItem?) {
        if (value != null) {
            setValue(value.toSelectItem().first)
        }
    }

    private fun onOptionsChanged(options: List<T>?) {
        if (options != null) {
            super.refresh()
            optionMap.setState(createMap(options))
        }
    }

    private fun createMap(options: List<SelectItem>): List<StringPair> {
        return options.map { it.toSelectItem() }
    }
}

fun <T: SelectItem> Container.select(
    value: VModel<T?>,
    properties: Properties<T>,
    attributes: Attributes = Attributes(),
    parent: Container? = null
): Select<T> {
    val component = SelectComponent(value, properties, attributes).also {
        if (parent == null) add(it)
        else parent.add(it)
    }

    return Select(component, properties)
}