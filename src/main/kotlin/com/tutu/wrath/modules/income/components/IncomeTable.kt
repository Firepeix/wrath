package com.tutu.wrath.modules.income.components

import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.table
import com.tutu.wrath.modules.accounting.models.Frequency
import com.tutu.wrath.modules.income.models.Income
import com.tutu.wrath.modules.user.models.User
import com.tutu.wrath.util.Money
import io.kvision.core.Container
import io.kvision.html.Div
import io.kvision.types.LocalDateTime

class IncomeTable : Div() {

    private val columns = listOf(
        Column("name", "Nome"),
        Column("amount", "Valor"),
        Column("origin", "Origem")
    )

    private val incomes = listOf(
        Income(name = "Teste", amount = Money(amount = 1110), origin = User("Teste"), data = LocalDateTime(), frequency = Frequency.ONCE),
        Income(name = "Teste 1", amount = Money(amount = 31330), origin = User("Teste"), data = LocalDateTime(), frequency = Frequency.MONTHLY),
        Income(name = "Teste 2", amount = Money(amount = 0), origin = User("Teste"), data = LocalDateTime(), frequency = Frequency.DAILY),
        Income(name = "Teste 3", amount = Money(amount = 10), origin = User("Teste"), data = LocalDateTime(), frequency = Frequency.QUARTELY),
        Income(name = "Teste 4", amount = Money(amount = 130), origin = User("Teste"), data = LocalDateTime(), frequency = Frequency.WEEKLY),
    )

    init {
        table(header = "Entradas", columns = columns, rows = incomes.map { Row(it) })
    }
}

fun Container.incomeTable() : IncomeTable {
    val component = IncomeTable()
    this.add(component)
    return component
}