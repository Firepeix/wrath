package com.tutu.wrath.modules.user.components

import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.table
import com.tutu.wrath.modules.user.model.User
import com.tutu.wrath.modules.user.model.UserBalance
import com.tutu.wrath.modules.user.usecases.UserUseCase
import com.tutu.wrath.util.unwrap
import io.kvision.core.Container
import io.kvision.core.Display
import io.kvision.core.Overflow
import io.kvision.core.StringPair
import io.kvision.html.Div
import io.kvision.snabbdom.VNode
import io.kvision.state.ObservableValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserBalanceTable(private val useCase: UserUseCase) : Div(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private val columns = listOf(
        Column("pay", "A Pagar", colspan = 2),
        Column("receive", "A Receber", colspan = 2),
    )

    private val balance: ObservableValue<UserBalance?> = ObservableValue(null)
    private val rows = ObservableValue(emptyList<Row>())
    private val users = ObservableValue(emptyList<User>())
    private val userOptions = ObservableValue(emptyList<StringPair>())
    private val userId: ObservableValue<String?> = ObservableValue(null)

    init {
        display = Display.TABLE
        overflow = Overflow.SCROLL

        balanceTableHeader(userId, userOptions)
        table(columns, rows)
    }

    override fun afterInsert(node: VNode) {
        setHooks()
        initialize()
    }

    private fun setHooks() {
        users.subscribe { users ->
            users.firstOrNull()?.let { getBalance(it.id) }
            userOptions.setState(users.map { it.id to it.name })
        }

        userId.subscribe { id -> if(id != null) getBalance(id)  }
    }

    private fun initialize() {
        launch {
            users.setState(useCase.getFriends().unwrap(emptyList()))
        }
    }

    private fun getBalance(userId: String?) {
        console.log(userId)
    }

}

fun Container.userBalanceTable(useCase: UserUseCase) : UserBalanceTable {
    val component = UserBalanceTable(useCase)
    this.add(component)
    return component
}

