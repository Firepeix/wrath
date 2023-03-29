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

    fun a() {
        console.log("as")
    }
}

class Row(value: RowValue) : RowValue by value


