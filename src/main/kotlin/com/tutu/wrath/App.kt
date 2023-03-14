package com.tutu.wrath

import com.tutu.wrath.anger.layout.header
import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.module
import io.kvision.panel.root
import io.kvision.require
import io.kvision.startApplication
import com.tutu.wrath.anger.layout.main as mainApp

class App : Application() {
    init {
        require("css/anger.css")
        require("css/main.css")
    }

    override fun start(state: Map<String, Any>) {
        //I18n.manager =
        //    DefaultI18nManager(
        //        mapOf(
        //            "pl" to require("i18n/messages-pl.json"),
        //            "en" to require("i18n/messages-en.json")
        //        )
        //    )

        root("kvapp") {
            header()
            mainApp()
        }
    }

    override fun dispose(): Map<String, Any> {
        return mapOf()
    }
}

fun main() {
    startApplication(::App, module.hot, CoreModule)
}