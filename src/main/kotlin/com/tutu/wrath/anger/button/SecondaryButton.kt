package com.tutu.wrath.anger.button

import io.kvision.core.Container
import io.kvision.html.Button
import io.kvision.html.button
import io.kvision.panel.SimplePanel
import org.w3c.dom.events.MouseEvent

class SecondaryButton(text: String, handler: ActionButton) : SimplePanel() {

    init {
        button(text).onClick(handler)
    }
}

