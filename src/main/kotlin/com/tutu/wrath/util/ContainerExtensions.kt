package com.tutu.wrath.util

import io.kvision.core.Container
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

