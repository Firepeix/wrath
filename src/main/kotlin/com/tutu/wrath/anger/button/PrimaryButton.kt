package com.tutu.wrath.anger.button

import io.kvision.core.Container
import io.kvision.html.Button

class PrimaryButton(text: String, handler: ActionButton) : Button(text, className = "inline-block rounded bg-primary px-6 pt-2.5 pb-2 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)]") {

    init {
        setAttribute("data-te-ripple-init", "")
        setAttribute("data-te-ripple-color", "light")
        onClick(handler)
    }
}

fun Container.primaryButton(text: String, handler: ActionButton) : PrimaryButton {
    val component = PrimaryButton(text, handler)
    this.add(component)
    return component
}
