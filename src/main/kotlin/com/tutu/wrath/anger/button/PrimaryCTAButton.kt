package com.tutu.wrath.anger.button

import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.UNIT
import io.kvision.html.Button

class PrimaryCTAButton(text: String, handler: ActionButton) : Button(text, className = "inline-block rounded dark:bg-primary dark:border-none border-2 border-primary px-6 pt-2 dark:pt-2.5 pb-[6px] text-xs font-medium uppercase leading-normal text-primary dark:text-white transition duration-150 ease-in-out hover:border-primary-600 hover:bg-neutral-500 hover:bg-opacity-10 hover:text-primary-600 focus:border-primary-600 focus:text-primary-600 focus:outline-none focus:ring-0 active:border-primary-700 active:text-primary-700  dark:hover:bg-primary-500 dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)]") {

    init {
        height = CssSize(33, UNIT.px)
        minHeight = CssSize(33, UNIT.px)
        paddingTop = CssSize(0.4, UNIT.rem)
        setAttribute("data-te-ripple-init", "")
        onClick(handler)
    }
}

fun Container.primaryCTAButton(text: String, handler: ActionButton) : PrimaryCTAButton {
    val component = PrimaryCTAButton(text, handler)
    this.add(component)
    return component
}

