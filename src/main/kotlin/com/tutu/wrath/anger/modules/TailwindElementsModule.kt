package com.tutu.wrath.anger.modules

import io.kvision.ModuleInitializer
import io.kvision.require
import org.w3c.dom.HTMLElement

object TailwindElementsModule : ModuleInitializer {

    internal val elements = require("tw-elements")

    override fun initialize() {

    }

    fun createSelect(initialValue: String?, element: HTMLElement) {
        val select = elements.Select.getOrCreateInstance(element)
        if (initialValue != null) {
            select.setValue(initialValue)
        }
    }

    fun getValue(element: HTMLElement): String? {
        return elements.Select.getInstance(element).value as? String
    }

    fun setValue(value: String, element: HTMLElement) {
        elements.Select.getInstance(element)?.setValue(value)
    }
}