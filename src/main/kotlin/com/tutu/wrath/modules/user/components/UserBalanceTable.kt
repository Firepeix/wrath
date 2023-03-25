package com.tutu.wrath.modules.user.components

import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.table
import com.tutu.wrath.modules.user.model.User
import com.tutu.wrath.modules.user.model.UserBalance
import com.tutu.wrath.util.unwrap
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.html.Div
import io.kvision.snabbdom.VNode
import io.kvision.state.ObservableValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias GetFriends = suspend () -> Result<List<User>>
typealias GetBalance = suspend (userId: String) -> Result<UserBalance>
typealias CalculateBalance = (balance: UserBalance) -> List<Row>

class UserBalanceTable(
    private val getFriends: GetFriends,
    private val getBalance: GetBalance,
    private val calculateBalance: CalculateBalance,
) : Div(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

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
        balanceTableHeader(userId, userOptions)
        table(columns, rows)
    }

    override fun afterInsert(node: VNode) {
        setHooks()
        initialize()
    }

    private fun setHooks() {
        users.subscribe { users ->
            users.firstOrNull()?.let { setBalance(it.id) }
            userOptions.setState(users.map { it.id to it.name })
        }

        userId.subscribe { id -> if(id != null) setBalance(id)  }

        balance.subscribe { if(it != null)  calculateBalance(it) }
    }

    private fun initialize() {
        launch {
            users.setState(getFriends().unwrap(emptyList()))
        }
    }

    private fun setBalance(userId: String?) {
        launch {
            userId?.let {
                balance.setState(getBalance(it).unwrap(null))
            }
        }
    }

}

fun Container.userBalanceTable(getFriends: GetFriends, getBalance: GetBalance, calculateBalance: CalculateBalance) : UserBalanceTable {
    val component = UserBalanceTable(getFriends, getBalance, calculateBalance)
    this.add(component)
    return component
}

