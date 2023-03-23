package com.tutu.wrath.util

import com.tutu.wrath.anger.display.Display
import io.kvision.core.Color
import io.kvision.core.CssSize
import io.kvision.core.FontWeight
import io.kvision.core.UNIT

data class Money(private val amount: Int) {

    val reversedAmount = amount.toString().reversed()

    val color = when {
        amount > 0 -> Color("#21BA45")
        amount == 0 -> Color("#1976D2")
        else -> Color("#C10015")
    }

    fun String.parse(): Money {
        val sign = if (this[0] == '-') -1 else 1
        return Money(replace("/\\D/", "").toInt() * sign)
    }

    fun display(highlight: Boolean = false): Display {
        val content = toReal()
        return if (!highlight) Display(content) else Display(
            content = content,
            color = color,
            weight = FontWeight.BOLD,
            size = CssSize(1, UNIT.rem)
        )
    }

    fun toReal(showSign: Boolean = true): String {
        if (amount == 0) return "R$ 0,00"
        val isInCents = amount > -100 && amount < 100
        val sign = if (showSign && amount < 0) "-" else ""
        val cents = if (isInCents) "0" else ""
        val wholeNumber = wholeNumber()
        return "$sign R$ $wholeNumber$cents,${reversedAmount.slice(0 .. 1)}"
    }
    fun wholeNumber(): String {
        val unit = mutableListOf<String>()
        val amountAsString = amount.toString()
        val size = amount.toString().length - 3
        val formatted = if (amount > 0) amountAsString.slice(0 .. size) else amountAsString.slice(1..size)

        formatted.split("").filter { it != "" }.reversed().forEachIndexed { index, number ->
            if(index % 3 == 0 && index != 0) {
                unit.add(".")
            }
            unit.add(number)
        }

        return unit.reversed().joinToString("")
    }
}


