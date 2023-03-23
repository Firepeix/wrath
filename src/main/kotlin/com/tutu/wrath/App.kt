package com.tutu.wrath

import com.tutu.wrath.anger.layout.header
import com.tutu.wrath.anger.modules.TailwindElementsModule
import com.tutu.wrath.util.Manager
import io.kvision.*
import io.kvision.panel.root
import com.tutu.wrath.anger.layout.main as mainApp

class App : Application() {


    init {
        require("css/anger.css")
        require("css/main.css")
    }

    private fun getDependencyManager(): Manager {
        return Manager()
    }

    override fun start(state: Map<String, Any>) {
        val manager = getDependencyManager()
        //I18n.manager =
        //    DefaultI18nManager(
        //        mapOf(
        //            "pl" to require("i18n/messages-pl.json"),
        //            "en" to require("i18n/messages-en.json")
        //        )
        //    )

        root("kvapp") {
            header()
            mainApp(manager)
        }
    }

    override fun dispose(): Map<String, Any> {
        return mapOf()
    }
}

fun main() {
    startApplication(::App, module.hot, CoreModule, ToastifyModule, TomSelectDefaultModule, TailwindElementsModule)
}

