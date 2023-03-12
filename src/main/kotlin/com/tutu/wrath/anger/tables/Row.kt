package com.tutu.wrath.anger.tables

interface RowValue {
    fun getRowValue(id: String): String {
        return "-"
    }
}

class Row(value: RowValue) : RowValue by Value(value)


internal data class Value(val value : RowValue) : RowValue by value