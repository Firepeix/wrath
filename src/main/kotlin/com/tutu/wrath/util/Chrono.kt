package com.tutu.wrath.util

import io.kvision.types.LocalDateTime

data class Chrono(val date: LocalDateTime) {

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
    }
}

fun String.generalizeDate(): String {
    if (split(" ").size < 2) {
        return plus(" 00:00:00")
    }

    return this
}