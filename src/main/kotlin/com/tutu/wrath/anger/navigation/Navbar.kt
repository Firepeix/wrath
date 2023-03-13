package com.tutu.wrath.anger.navigation

import io.kvision.core.Container
import io.kvision.html.*

fun Container.navbar() {
    nav(className = "navbar bg-base-100") {
        div(className = "flex-none") { button("", className = "btn btn-square btn-ghost") {
            customTag("svg", className = "inline-block w-5 h-5 stroke-current", attributes = mapOf(
                "xmlns" to "http://www.w3.org/2000/svg",
                "viewBox" to "0 0 24 24",
                "fill" to "none",
               )) {
                customTag("path", attributes = mapOf(
                    "stroke-linecap" to "round",
                    "stroke-linejoin" to "round",
                    "stroke-width" to "2",
                    "d" to "M4 6h16M4 12h16M4 18h16",
                ))
            }
        } }
        div(className = "flex-1") { link("Ebisu", className = "btn btn-ghost normal-case text-xl") }
        div(className = "flex-none") { button("", className = "btn btn-square btn-ghost") {
            customTag("svg", className = "inline-block w-5 h-5 stroke-current", attributes = mapOf(
                "xmlns" to "http://www.w3.org/2000/svg",
                "viewBox" to "0 0 24 24",
                "fill" to "none",
            )) {
                customTag("path", attributes = mapOf(
                    "stroke-linecap" to "round",
                    "stroke-linejoin" to "round",
                    "stroke-width" to "2",
                    "d" to "M5 12h.01M12 12h.01M19 12h.01M6 12a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0z",
                ))
            }
        } }
    }
}

