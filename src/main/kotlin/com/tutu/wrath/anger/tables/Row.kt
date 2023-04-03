package com.tutu.wrath.anger.tables

import com.tutu.wrath.anger.display.Display

interface RowValue {
    companion object {
        fun v(position: Int, vararg displays: Display) = displays.getOrElse(position) { Display(content = "-") }
    }


    fun getRowValue(id: String, position: Int = 0): Display {
        return Display(content = "-")
    }

    fun shouldHighlight(): Boolean {
        return false
    }
}

class Row(value: RowValue) : RowValue by value

class DisplayRow(vararg val displays: Display): RowValue {
    override fun getRowValue(id: String, position: Int): Display {
        return displays.getOrNull(position) ?: super.getRowValue(id, position)
    }
}



