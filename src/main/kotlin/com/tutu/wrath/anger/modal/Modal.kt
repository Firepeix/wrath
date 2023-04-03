package com.tutu.wrath.anger.modal

import com.tutu.wrath.anger.layout.attributes
import io.kvision.core.Container
import io.kvision.html.Button
import io.kvision.html.Div
import io.kvision.html.div
import io.kvision.html.h5

typealias ModalBody = Div.(Modal) -> Unit
typealias ModalFooter = Div.() -> Unit
typealias OnDisplay = () -> Unit
class Modal(
    name: String,
    title: String,
    var onDisplay: OnDisplay? = null,
    noPadding: Boolean = false,
    body: ModalBody,
    innerFooter: ModalFooter?
) : Div(className = "fixed top-0 left-0 z-[1055] hidden h-full w-full overflow-y-auto overflow-x-hidden outline-none") {

    private val displayButton = Button("", className = "hidden").apply { attributes("data-te-toggle" to "modal", "data-te-target" to "#$name") }

    init {
        val self = this
        attributes("id" to name, "data-te-modal-init" to "", "tabindex" to "-1", "aria-labelledby" to name, "aria-hidden" to "true")
        add(displayButton)
        div(className = "pointer-events-none relative w-auto translate-y-[-50px] opacity-0 transition-all duration-300 ease-in-out min-[576px]:mx-auto min-[576px]:mt-7 min-[576px]:max-w-[500px]") {
            setAttribute("data-te-modal-dialog-ref", "")
            div(className = "min-[576px]:shadow-[0_0.5rem_1rem_rgba(#000, 0.15)] pointer-events-auto relative flex w-full flex-col rounded-md border-none bg-white bg-clip-padding text-current shadow-lg outline-none dark:bg-neutral-600") {
                title(title)
                body(self, body, noPadding)
                innerFooter?.let { footer(it) }
            }
        }
    }


    fun toggle() {
        displayButton.getElement()?.click()
        val isOpen = getAttribute("data-te-open") == null
        if (isOpen) onDisplay?.invoke()
    }

    companion object {
        internal fun Div.title(title: String) {
            div(className = "flex flex-shrink-0 items-center justify-between rounded-t-md border-b-2 border-neutral-100 border-opacity-100 p-3 dark:border-opacity-50") {
                h5(title, className = "text-xl font-medium leading-normal text-neutral-800 dark:text-neutral-200")
            }
        }

        internal fun Div.body(instance: Modal, body: ModalBody, noWidthPadding: Boolean) {
            div(className = "relative flex-auto") {
                if (!noWidthPadding) addCssClass("p-4")
                attributes("data-te-modal-body-ref" to "")
                body(instance)
            }
        }

        internal fun Div.footer(footer: ModalFooter) {
            div(className = "flex flex-shrink-0 flex-wrap items-center justify-end rounded-b-md border-t-2 border-neutral-100 border-opacity-100 p-4 dark:border-opacity-50") {
                footer()
            }
        }
    }

}

fun Container.modal(name: String, title: String, onDisplay: OnDisplay? = null, noPadding: Boolean = false, body: ModalBody, footer: ModalFooter?): Modal {
    val component = Modal(name, title, onDisplay, noPadding, body, footer)
    this.add(component)
    return component
}

fun Container.modal(name: String, title: String, onDisplay: OnDisplay? = null, noPadding: Boolean = false, body: ModalBody): Modal {
    return modal(name, title, onDisplay, noPadding, body, null)
}
