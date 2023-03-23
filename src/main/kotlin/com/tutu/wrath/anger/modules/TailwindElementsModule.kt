package com.tutu.wrath.anger.modules

import io.kvision.ModuleInitializer
import io.kvision.require

object TailwindElementsModule : ModuleInitializer {

    internal val elements = require("tw-elements")

    override fun initialize() {

    }
}