package com.tutu.wrath.util

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.anger.form.select.SelectItem
import io.kvision.core.StringPair
import io.kvision.types.LocalDateTime

data class Chrono(val date: LocalDateTime) {

    enum class Month(val ptBr: String) : SelectItem {
        JANUARY("Janeiro"),
        FEBRUARY("Fevereiro"),
        MARCH("Março"),
        APRIL("Abril"),
        MAY("Maio"),
        JUNE("Junho"),
        JULY("Julho"),
        AUGUST("Agosto"),
        SEPTEMBER("Setembro"),
        OCTOBER("Outubro"),
        NOVEMBER("Novembro"),
        DECEMBER("Dezembro");

        override fun toSelectItem(): StringPair {
            return ordinal.toString() to ptBr
        }

        fun distance(to: Month): Int  = to.ordinal - ordinal
    }

    constructor(value: String) : this(parseToDate(value))

    companion object {
        internal fun parseToDate(value: String): LocalDateTime {
            if (value.contains("-")) {
                return parseIsoToDate(value)
            }

            return parseBrtToDate(value)
        }

        private fun parseIsoToDate(value: String): LocalDateTime {
            val (date, time) = value.generalizeDate().split(" ")
            val (year, month, day) = date.split("-")
            val (hour, minute, second) = time.split(":")

            return LocalDateTime(year.toInt(), month.toInt(), day.toInt(), hour.toInt(), minute.toInt(), second.toInt())
        }

        private fun parseBrtToDate(value: String): LocalDateTime {
            val (date, time) = value.generalizeDate().split(" ")

            return parseIsoToDate(date.split("/").reversed().joinToString("-") + " $time")
        }

        fun parse(value: String) = Chrono(value)

        fun now() = Chrono(LocalDateTime())
    }

    val month = Month.values()[date.getMonth()]

    fun displayDate(): Display {
        return Display("${date.getDay().leadingZero()}/${date.getUTCMonth().leadingZero()}/${date.getUTCFullYear()}")
    }
}

fun String.generalizeDate(): String {
    if (split(" ").size < 2) {
        return plus(" 00:00:00")
    }

    return this
}

fun Int.leadingZero() = if (this < 10) "0$this" else "$this"