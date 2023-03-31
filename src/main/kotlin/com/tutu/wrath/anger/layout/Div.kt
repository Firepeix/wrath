package com.tutu.wrath.anger.layout

import io.kvision.core.Container

internal fun Container.classname(vararg className: String) {
    className.forEach { addCssClass(it) }
}

internal fun Container.classname(className: String) {
    className.split(" ").forEach { addCssClass(it) }
}
