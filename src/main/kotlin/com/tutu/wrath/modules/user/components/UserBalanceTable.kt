package com.tutu.wrath.modules.user.components

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.anger.form.select.Select
import com.tutu.wrath.anger.tables.Column
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.tableDeprecated
import com.tutu.wrath.modules.user.model.User
import com.tutu.wrath.modules.user.model.UserBalance
import com.tutu.wrath.modules.user.model.UserBeneficiaryExpense
import com.tutu.wrath.util.Money
import com.tutu.wrath.util.unwrap
import io.kvision.core.Container
import io.kvision.core.CssSize
import io.kvision.core.UNIT
import io.kvision.html.Div
import io.kvision.snabbdom.VNode
import io.kvision.state.ObservableValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias GetFriends = suspend () -> Result<List<User>>
typealias GetBalance = suspend (userId: String) -> Result<UserBalance>


class UserBalanceTable(
    private val getFriends: GetFriends,
    private val getBalance: GetBalance,
) : Div(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private val columns = listOf(
        Column("payExpense", "A Pagar", colspan = 2),
        Column("receiveExpense", "A Receber", colspan = 2),
    )

    private val balance: ObservableValue<Pair<User?, UserBalance?>> = ObservableValue(Pair<User?, UserBalance?>(null, null))
    private val rows = ObservableValue(emptyList<Row>())

    private var userSelect: Select<User>? = null
    private var users = emptyList<User>()
        set(value) { field = value
            userSelect?.properties?.options = value
            value.firstOrNull()?.let { setBalance(it) }
        }

    private val chosenUser: ObservableValue<User?> = ObservableValue(null)
    private val summary: ObservableValue<Display?> = ObservableValue(null)

    init {
        userSelect = balanceTableHeader(chosenUser, users)
        tableDeprecated(columns, rows, footer = summary)
    }

    override fun afterInsert(node: VNode) {
        setHooks()
        initialize()
    }

    private fun setHooks() {
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
            users = getFriends().unwrap(emptyList())
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

    private fun createRows(balance: UserBalance): List<Row> {
        val rows = mutableListOf<Row>()
        for (index in 0..getMaxRows(balance)) {
            val row = UserBeneficiaryExpense(balance.friendBeneficiaryExpenses.getOrNull(index), balance.userBeneficiaryExpenses.getOrNull(index))
            rows.add(Row(row))
        }

        return rows
    }

    private fun getMaxRows(balance: UserBalance): Int {
        val count = if (balance.friendBeneficiaryExpenses.size > balance.userBeneficiaryExpenses.size) balance.friendBeneficiaryExpenses.size
        else balance.userBeneficiaryExpenses.size
        return count - 1
    }
}

fun Container.userBalanceTable(getFriends: GetFriends, getBalance: GetBalance) : UserBalanceTable {
    val component = UserBalanceTable(getFriends, getBalance)
    this.add(component)
    return component
}

