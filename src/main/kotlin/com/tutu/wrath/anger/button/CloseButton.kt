package com.tutu.wrath.anger.button

import com.tutu.wrath.anger.layout.attributes
import com.tutu.wrath.anger.layout.classname
import io.kvision.core.Container
import io.kvision.html.Button
import io.kvision.html.customTag

class CloseButton(text: String, handler: ActionButton) : Button(text, className = "box-content rounded-none border-none hover:no-underline hover:opacity-75 focus:opacity-100 focus:shadow-none focus:outline-none") {

    init {
        setAttribute("data-te-modal-dismiss", "")
        customTag("svg") {
            classname("h-6", "w-6")
            attributes("xmlns" to "http://www.w3.org/2000/svg", "fill" to "none", "viewBox" to "0 0 24 24", "stroke-width" to "1.5", "stroke" to "currentColor")
            customTag("path") {
                attributes("stroke-linecap" to "round", "stroke-linejoin" to "round", "d" to "M6 18L18 6M6 6l12 12" )
            }
        }
        onClick(handler)
    }
}

fun Container.closeButton(text: String, handler: ActionButton) : CloseButton {
    val component = CloseButton(text, handler)
    this.add(component)
    return component
}

