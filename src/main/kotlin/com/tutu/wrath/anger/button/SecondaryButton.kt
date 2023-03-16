package com.tutu.wrath.anger.button

import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.UNIT
import io.kvision.html.Button

class SecondaryButton(text: String, handler: ActionButton) : Button(text, className = "btn btn-secondary") {

    init {
        borderRadius = CssSize(4, UNIT.px)
        onClick(handler)
    }
}

fun Container.secondaryButton(text: String, handler: ActionButton) : SecondaryButton {
    val component = SecondaryButton(text, handler)
    this.add(component)
    return component
}

