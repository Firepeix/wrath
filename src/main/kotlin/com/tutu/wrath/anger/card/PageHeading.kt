package com.tutu.wrath.anger.card

import io.kvision.core.Container
import io.kvision.html.Button
import io.kvision.html.div
import io.kvision.html.h2


fun Container.pageHeading(title: String, mainAction: (Container.() -> Button)) {
    div(className = "flex w-full justify-between rounded bg-white p-4 border dark:bg-neutral-700 dark:border-neutral-800") {
        h2(title, className = "text-xl font-bold leading-loose text-neutral-800 dark:text-neutral-50")

        mainAction().apply(::ctaButtonStyle)
    }

}


fun ctaButtonStyle(button: Button) {

}