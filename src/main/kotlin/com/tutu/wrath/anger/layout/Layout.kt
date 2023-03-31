package com.tutu.wrath.anger.layout

import io.kvision.core.Container
import io.kvision.core.StringPair

fun Container.attributes(vararg attributes: StringPair) {
    attributes.forEach { (name, value) -> setAttribute(name, value) }
}