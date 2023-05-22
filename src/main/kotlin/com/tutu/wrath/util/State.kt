package com.tutu.wrath.util



class State<P: StateProperties<L>, L> constructor(private val properties: P, init: L.() -> Unit) {
    init {
        init.invoke(properties.listeners)
    }
}

interface StateProperties<L> {
    val listeners: L
}

abstract class StateScope<L>{
    abstract val listeners: L

    fun init(block: L.() -> Unit) {
        block.invoke(listeners)
    }
}

abstract class StatefullWidget<S> {
    protected abstract val state: S

    fun changeState(block: S.() -> Unit) {
        block(state)
    }

}