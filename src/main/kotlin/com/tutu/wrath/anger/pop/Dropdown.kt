package com.tutu.wrath.anger.pop

import com.tutu.wrath.anger.layout.attributes
import com.tutu.wrath.anger.modules.tailwind.TailwindElementsModule
import com.tutu.wrath.anger.modules.tailwind.dropdown.DropdownOptions
import com.tutu.wrath.anger.modules.tailwind.dropdown.TwDropdown
import io.kvision.core.Container
import io.kvision.html.Div
import io.kvision.html.Span
import io.kvision.html.li
import io.kvision.html.ul
import io.kvision.snabbdom.VNode


interface DropdownActionScope {
    fun toggle()
}

interface DropdownScope: DropdownActionScope {
    fun change(scope: (DropdownActionScope) -> Unit) {
        scope.invoke(this)
    }
}

class Dropdown(private val actuator: Container): Div(className = "relative"), DropdownScope {

    private var dropdown: TwDropdown? = null
    private var noop = Span()

    init {
        add(noop)
        ul(className = "absolute z-[1000] float-left m-0 hidden min-w-max list-none overflow-hidden rounded-lg border-none bg-white bg-clip-padding text-left text-base shadow-lg dark:bg-neutral-700 [&[data-te-dropdown-show]]:block") {
            attributes("data-te-dropdown-menu-ref" to "")
            li("Action")
            li("Another action")
            li("Something else here")
        }
    }

    override fun afterInsert(node: VNode) {
        initialize()
    }

    fun initialize() {
        actuator.getElement()?.let { parent ->
            noop.getElement()?.let {
                dropdown = TailwindElementsModule.createDropdown(it, DropdownOptions(reference = DropdownOptions.Reference.Container(parent)))
            }
        }
    }

    override fun toggle() {
        dropdown?.toggle()
    }

}

fun Container.dropdown(actuator: Container? = null): DropdownScope {
    return Dropdown(actuator ?: this).also { add(it) }
}