package com.tutu.wrath.anger.tables

data class Column(val id: String, val label: String, val colspan: Int = 1) {

    val isSubdivided = colspan > 1
    fun isNextSection(position: Int, rowPosition: Int = 1): Boolean = isSubdivided && (position + 1) % colspan == 0 && rowPosition == 0
}