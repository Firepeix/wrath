package com.tutu.wrath.util

class State<P: StateProperties<L>, L> constructor(private val properties: P, init: L.() -> Unit) {
    init {
        init.invoke(properties.listeners)
    }
}

interface StateProperties<L> {
    val listeners: L
}