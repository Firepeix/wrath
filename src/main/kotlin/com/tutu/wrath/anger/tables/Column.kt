package com.tutu.wrath.anger.tables


data class ColumnMaker(val columns: MutableList<Column> = mutableListOf()) {
    fun column(id: String, label: String, colspan: Int = 1, shouldFilter: Boolean = true): Column {
        val column = Column(id, label, colspan, shouldFilter)
        columns.add(column)
        return column
    }
}


data class Column(val id: String, val label: String, val colspan: Int = 1, val shouldFilter: Boolean = true) {

    val isSubdivided = colspan > 1
    fun isNextSection(position: Int, rowPosition: Int = 1): Boolean = isSubdivided && (position + 1) % colspan == 0 && rowPosition == 0
}