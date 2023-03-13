package com.tutu.wrath.anger.layout

import com.tutu.wrath.anger.button.SecondaryButton
import com.tutu.wrath.anger.card.pageHeading
import io.kvision.core.Container
import io.kvision.html.span
import io.kvision.html.main as baseMain

fun Container.main() {
    baseMain(className = "p-8 px-4") {
        pageHeading("Despesas", SecondaryButton("Ver Resumo") {
            console.log("asd")
        })
    }
}