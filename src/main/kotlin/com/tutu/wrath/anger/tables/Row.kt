package com.tutu.wrath.anger.tables

import com.tutu.wrath.anger.display.Display

interface RowValue {
    fun getRowValue(id: String): Display {
        return Display(
            content = "-"
        )
    }
}

class Row(value: RowValue) : RowValue by value


