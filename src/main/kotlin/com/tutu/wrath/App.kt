package com.tutu.wrath

import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.table
import com.tutu.wrath.modules.accounting.models.Frequency
import com.tutu.wrath.modules.income.models.Income
import com.tutu.wrath.modules.user.models.User
import com.tutu.wrath.util.Money
import io.kvision.Application
import io.kvision.BootstrapCssModule
import io.kvision.BootstrapModule
import io.kvision.CoreModule
import io.kvision.html.div
import io.kvision.i18n.DefaultI18nManager
import io.kvision.i18n.I18n
import io.kvision.i18n.I18n.tr
import io.kvision.module
import io.kvision.panel.root
import io.kvision.require
import io.kvision.startApplication
import io.kvision.types.LocalDateTime

class App : Application() {
    init {
        //require("css/kvapp.css")
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
            table(
                listOf(
                    Column("name", "Nome"),
                    Column("amount", "Valor"),
                    Column("origin", "Origem"),
                ),
                listOf(
                    Row(Income(name = "Viagem", amount = Money(20000), origin =  User("Tatielle"), data = LocalDateTime(), frequency = Frequency.ONCE)),
                    Row(Income(name = "Hotel", amount = Money(15600), origin = User("Wendy"), data = LocalDateTime(), frequency = Frequency.ONCE)),
                    Row(Income(name = "Niver", amount = Money(9800), origin = User("Mãe"), data = LocalDateTime(), frequency = Frequency.ONCE)),
                    Row(Income(name = "Niver", amount = Money(-41), origin = User("a"), data = LocalDateTime(), frequency = Frequency.ONCE)),
                    Row(Income(name = "Salario", amount = Money(898990), origin = User("Picpay"), data = LocalDateTime(), frequency = Frequency.ONCE)),
                )
            )
        }
    }

    override fun dispose(): Map<String, Any> {
        return mapOf()
    }
}

fun main() {
    startApplication(::App, module.hot, CoreModule)
}