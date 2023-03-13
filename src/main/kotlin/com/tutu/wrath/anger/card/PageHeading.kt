package com.tutu.wrath.anger.card

import com.tutu.wrath.anger.button.SecondaryButton
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.UNIT
import io.kvision.html.div
import io.kvision.html.h2

fun Container.pageHeading(title: String, mainAction: SecondaryButton) {
    div(className = "card w-96 border-neutral-content text-accent-300 card-bordered w-full") {
        borderRadius = CssSize(8, UNIT.px)

        div(className = "card-body p-4 py-5") {
            h2(title, className = "card-title text-xl")
            add(mainAction.also {
                addCssClass("card-actions")
                addCssClass("justify-end")
            })
        }
    }
}
