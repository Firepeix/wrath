package com.tutu.wrath.anger.display

import io.kvision.core.Color
import io.kvision.core.Color.Companion.rgb
import io.kvision.core.CssSize
import io.kvision.core.FontWeight
import io.kvision.core.UNIT

data class Display(
    val content: String,
    val size: CssSize = CssSize(0.875, UNIT.rem),
    val color: Color? = null,
    val weight: FontWeight = FontWeight.NORMAL
) {
    val fontColor = convertColor(color)

    companion object {
        fun convertColor(color: Color?): Color? {
            val badColor = rgb(7, 55, 99)
            if (color?.color == badColor.color) {
                return rgb(0, 94, 181)
            }

            return color
        }
    }
}
