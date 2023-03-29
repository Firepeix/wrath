package com.tutu.wrath.modules.user.components

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.table
import com.tutu.wrath.modules.user.model.User
import com.tutu.wrath.modules.user.model.UserBalance
import com.tutu.wrath.util.Money
import com.tutu.wrath.util.unwrap
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.StringPair
import io.kvision.core.UNIT
import io.kvision.html.Div
import io.kvision.snabbdom.VNode
import io.kvision.state.ObservableValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias GetFriends = suspend () -> Result<List<User>>
typealias GetBalance = suspend (userId: String) -> Result<UserBalance>
typealias CreateRows = (balance: UserBalance) -> List<Row>


class UserBalanceTable(
    private val getFriends: GetFriends,
    private val getBalance: GetBalance,
    private val createRows: CreateRows,
) : Div(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private val columns = listOf(
        Column("payExpense", "A Pagar", colspan = 2),
        Column("receiveExpense", "A Receber", colspan = 2),
    )

    private val balance: ObservableValue<Pair<User?, UserBalance?>> = ObservableValue(Pair<User?, UserBalance?>(null, null))
    private val rows = ObservableValue(emptyList<Row>())
    private val users = ObservableValue(emptyList<User>())
    private val chosenUser: ObservableValue<User?> = ObservableValue(null)
    private val summary: ObservableValue<Display?> = ObservableValue(null)

    init {
        balanceTableHeader(chosenUser, users)
        table(columns, rows, footer = summary)
    }

    override fun afterInsert(node: VNode) {
        setHooks()
        initialize()
    }

    private fun setHooks() {
        users.subscribe { users ->  users.firstOrNull()?.let { setBalance(it) } }

        chosenUser.subscribe { if(it != null) setBalance(it)  }

        balance.subscribe { (user, balance) ->
            if(user != null && balance != null)  {
                rows.setState(createRows(balance))
                summary.setState(createSummary(user, balance))
            }
        }
    }

    private fun initialize() {
        launch {
            users.setState(getFriends().unwrap(emptyList()))
        }
    }

    private fun setBalance(user: User) {
        launch {
            balance.setState(user to getBalance(user.id).unwrap(null))
        }
    }

    private fun createSummary(user: User, userBalance: UserBalance): Display {
        val legend = when(userBalance.totalAmount.sign) {
            Money.Sign.POSITIVE -> "Receber de"
            Money.Sign.NEUTRAL -> "Não realizar operação com"
            Money.Sign.NEGATIVE -> "Enviar para"
        }


       return userBalance.totalAmount.highlight().copy(
           content = "$legend ${user.name}: ${userBalance.totalAmount.toReal()}",
           size = CssSize(1.3, UNIT.rem)
       )
    }
}

fun Container.userBalanceTable(getFriends: GetFriends, getBalance: GetBalance, calculateBalance: CreateRows) : UserBalanceTable {
    val component = UserBalanceTable(getFriends, getBalance, calculateBalance)
    this.add(component)
    return component
}

