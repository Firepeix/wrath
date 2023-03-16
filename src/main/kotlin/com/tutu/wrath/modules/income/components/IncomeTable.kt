package com.tutu.wrath.modules.income.components

import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.table
import com.tutu.wrath.modules.accounting.models.Frequency
import com.tutu.wrath.modules.income.dto.IncomeListResponse
import com.tutu.wrath.modules.income.models.Income
import com.tutu.wrath.modules.user.models.User
import com.tutu.wrath.util.Money
import com.tutu.wrath.util.Rocket
import io.kvision.core.Container
import io.kvision.html.Div
import io.kvision.snabbdom.VNode
import io.kvision.types.LocalDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class IncomeTable(private val rocket: Rocket) : Div(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Job()

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

    override fun afterInsert(node: VNode) {
        initialize()
    }

    private fun initialize() {
        launch {
            val response = rocket.get<IncomeListResponse>("ebisu/incomes")
            console.log(response)
        }
    }

}

fun Container.incomeTable(rocket: Rocket) : IncomeTable {
    val component = IncomeTable(rocket)
    this.add(component)
    return component
}

