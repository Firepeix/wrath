package com.tutu.wrath.util

import io.kvision.ModuleInitializer
import io.kvision.utils.obj

object ToastifyModule : ModuleInitializer {

    internal val toastify = io.kvision.require("toastify-js")

    override fun initialize() {
        io.kvision.require("toastify-js/src/toastify.css")
    }
}

object Toaster {
    const val ERROR_ICON = "<svg xmlns=\"http://www.w3.org/2000/svg\" class=\"stroke-current mr-4 flex-shrink-0 h-6 w-6\" fill=\"none\" viewBox=\"0 0 24 24\"><path stroke-linecap=\"round\" stroke-linejoin=\"round\" stroke-width=\"2\" d=\"M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z\"></path></svg>"

    fun danger(message: String) {
        Toast.danger("$ERROR_ICON $message", ToastOptions(
            position = ToastPosition.BOTTOMRIGHT,
            duration = 4 * 1000,
            escapeMarkup = false,
            className = "kv-toastify-danger"
        ))
    }
}


/**
 * Toast message types.
 */
internal enum class ToastType(internal val type: String) {
    PRIMARY("kv-toastify-primary"),
    SECONDARY("kv-toastify-secondary"),
    INFO("kv-toastify-info"),
    SUCCESS("kv-toastify-success"),
    WARNING("kv-toastify-warning"),
    DANGER(""),
    LIGHT("kv-toastify-light"),
    DARK("kv-toastify-dark"),
}

/**
 * Toast positions.
 */
enum class ToastPosition {
    TOPRIGHT,
    BOTTOMRIGHT,
    BOTTOMLEFT,
    TOPLEFT
}

/**
 * Toast options.
 */
data class ToastOptions(
    val position: ToastPosition? = null,
    val className: String? = null,
    val escapeMarkup: Boolean? = null,
    val close: Boolean? = null,
    val oldestFirst: Boolean? = null,
    val duration: Int? = null,
    val stopOnFocus: Boolean? = null,
    val onClick: (() -> Unit)? = null,
    val destination: String? = null,
    val newWindow: Boolean? = null,
    val callback: (() -> Unit)? = null,
    val avatar: String? = null,
    val offset: dynamic = null,
    val style: dynamic = null,
    val ariaLive: String? = null
)

internal fun ToastOptions.toJs(): dynamic {
    val positionType = when (position) {
        ToastPosition.TOPRIGHT, ToastPosition.BOTTOMRIGHT -> "right"
        ToastPosition.TOPLEFT, ToastPosition.BOTTOMLEFT -> "left"
        else -> null
    }
    val gravityType = when (position) {
        ToastPosition.TOPRIGHT, ToastPosition.TOPLEFT -> "top"
        ToastPosition.BOTTOMRIGHT, ToastPosition.BOTTOMLEFT -> "bottom"
        else -> null
    }
    return obj {
        if (positionType != null) this.position = positionType
        if (gravityType != null) this.gravity = gravityType
        if (escapeMarkup != null) this.escapeMarkup = escapeMarkup
        if (close != null) this.close = close
        if (oldestFirst != null) this.oldestFirst = oldestFirst
        if (duration != null) this.duration = duration
        if (stopOnFocus != null) this.stopOnFocus = stopOnFocus
        if (onClick != null) this.onClick = onClick
        if (destination != null) this.destination = destination
        if (newWindow != null) this.newWindow = newWindow
        if (callback != null) this.callback = callback
        if (avatar != null) this.avatar = avatar
        if (offset != null) this.offset = offset
        if (style != null) this.style = style
        if (ariaLive != null) this.ariaLive = ariaLive
    }
}

/**
 * Toast component object.
 */
object Toast {

    init {
        ToastifyModule.initialize()
    }

    /**
     * Shows a primary toast.
     * @param message a toast message
     * @param options toast options
     */
    fun primary(message: String, options: ToastOptions? = null) {
        show(ToastType.PRIMARY, message, options)
    }

    /**
     * Shows a secondary toast.
     * @param message a toast message
     * @param options toast options
     */
    fun secondary(message: String, options: ToastOptions? = null) {
        show(ToastType.SECONDARY, message, options)
    }

    /**
     * Shows a success toast.
     * @param message a toast message
     * @param options toast options
     */
    fun success(message: String, options: ToastOptions? = null) {
        show(ToastType.SUCCESS, message, options)
    }

    /**
     * Shows an info toast.
     * @param message a toast message
     * @param options toast options
     */
    fun info(message: String, options: ToastOptions? = null) {
        show(ToastType.INFO, message, options)
    }

    /**
     * Shows a warning toast.
     * @param message a toast message
     * @param options toast options
     */
    fun warning(message: String, options: ToastOptions? = null) {
        show(ToastType.WARNING, message, options)
    }

    /**
     * Shows a danger toast.
     * @param message a toast message
     * @param options toast options
     */

    fun danger(message: String, options: ToastOptions? = null) {
        show(ToastType.DANGER, message, options)
    }

    /**
     * Shows a light toast.
     * @param message a toast message
     * @param options toast options
     */
    fun light(message: String, options: ToastOptions? = null) {
        show(ToastType.LIGHT, message, options)
    }

    /**
     * Shows a dark toast.
     * @param message a toast message
     * @param options toast options
     */
    fun dark(message: String, options: ToastOptions? = null) {
        show(ToastType.DARK, message, options)
    }

    private fun show(type: ToastType, message: String, options: ToastOptions? = null) {
        val optJs = if (options != null) {
            val opts = options.toJs()
            opts["text"] = message
            opts["className"] = options.className ?: type.type
            opts
        } else {
            obj {
                this.text = message
                this.className = type.type
            }
        }
        ToastifyModule.toastify(optJs).showToast()
    }

}
