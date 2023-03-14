package com.tutu.wrath.anger.card

import io.kvision.core.AlignItems
import io.kvision.core.Border
import io.kvision.core.BorderStyle
import io.kvision.core.Color
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.core.UNIT
import io.kvision.html.Button
import io.kvision.html.Div
import io.kvision.html.div
import io.kvision.html.h2


fun Container.pageHeading(title: String, mainAction: (Container.() -> Button)) {
    div(className = "card w-96 border-neutral-content text-accent-300 card-bordered w-full") {
        borderRadius = CssSize(4, UNIT.px)
        border = Border(CssSize(1, UNIT.px), style = BorderStyle.SOLID, color = Color("#c6c6c633"))

        div(className = "card-body px-4") {
            apply(::headingStyle)

            h2(title, className = "card-title text-xl")
            mainAction().apply(::ctaButtonStyle)
        }
    }
}


fun headingStyle(heading: Div) {
    heading.flexDirection = FlexDirection.ROW
    heading.justifyContent = JustifyContent.SPACEBETWEEN
    heading.alignItems = AlignItems.CENTER
    heading.paddingTop = CssSize(0, UNIT.px)
    heading.paddingBottom = CssSize(0, UNIT.px)
    heading.height = CssSize(48, UNIT.px)
}

fun ctaButtonStyle(button: Button) {
    button.height = CssSize(33, UNIT.px)
    button.minHeight = CssSize(33, UNIT.px)
}