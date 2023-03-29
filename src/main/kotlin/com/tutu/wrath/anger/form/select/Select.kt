package com.tutu.wrath.anger.form.select

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.table
import com.tutu.wrath.modules.user.components.UserBalanceTable
import com.tutu.wrath.modules.user.components.balanceTableHeader
import com.tutu.wrath.modules.user.components.userBalanceTable
import com.tutu.wrath.modules.user.model.User
import com.tutu.wrath.modules.user.model.UserBalance
import com.tutu.wrath.util.Money
import com.tutu.wrath.util.unwrap
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.form.select.TomSelectCallbacks
import io.kvision.form.select.tomSelect
import io.kvision.html.Div
import io.kvision.snabbdom.VNode
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Select<T: SelectItem>(private val value: ObservableValue<T?>, private val options: ObservableValue<List<T>>, private val asHeader: Boolean = false) : Div() {

    private val optionsMap: ObservableValue<List<StringPair>> = ObservableValue(createMap(options.value))

    init {
        tomSelect(options = optionsMap.value,
            tsCallbacks = TomSelectCallbacks(onChange = ::onChange)) {
            if (asHeader)  addCssClass("header-mode")
        }.bind(optionsMap) {
            this.options = it
            placeholder = it.firstOrNull()?.second ?: ""
        }
    }

    override fun afterInsert(node: VNode) {
        setHooks()
    }

    private fun setHooks() {
        options.subscribe { options -> optionsMap.setState(createMap(options))}
    }

    private fun createMap(options: List<SelectItem>): List<StringPair> {
        return options.map { it.toSelectItem() }
    }

    private fun onChange(value: String?) {
        this.value.setState(options.value.firstOrNull { it.toSelectItem().first == value })
    }
}

fun <T: SelectItem> Container.select(value: ObservableValue<T?>, options: ObservableValue<List<T>>, asHeader: Boolean = false): Select<T> {
    val component = Select(value, options, asHeader)
    add(component)
    return component
}
