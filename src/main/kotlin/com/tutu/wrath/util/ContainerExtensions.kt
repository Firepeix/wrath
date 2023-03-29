package com.tutu.wrath.util

import io.kvision.core.Container

fun Container.toggleClass(css: String) {
    if (hasCssClass(css)) {
        removeCssClass(css)
    } else {
        addCssClass(css)
    }
}