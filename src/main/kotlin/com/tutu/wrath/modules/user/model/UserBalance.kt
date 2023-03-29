package com.tutu.wrath.modules.user.model

import com.tutu.wrath.anger.display.Display
import com.tutu.wrath.anger.tables.Row
import com.tutu.wrath.anger.tables.RowValue
import com.tutu.wrath.anger.tables.RowValue.Companion.v
import com.tutu.wrath.modules.expense.model.Expense
import com.tutu.wrath.util.Money

data class UserBalance(
    val totalAmount: Money,
    val userBeneficiaryExpenses: List<Expense>,
    val friendBeneficiaryExpenses: List<Expense>
)

data class UserBeneficiaryExpense(val payExpense: Expense?, val receiveExpense: Expense?) : RowValue {
    override fun getRowValue(id: String, position: Int): Display {
        return when(id) {
            "payExpense" -> v(position, Display(payExpense?.name ?: ""), payExpense?.amount?.display() ?: Display(""))
            "receiveExpense" -> v(position, Display(receiveExpense?.name ?: ""), receiveExpense?.amount?.display() ?: Display(""))
            else -> super.getRowValue(id, position)
        }
    }

    override fun shouldHighlight(): Boolean {
        return true
    }
}

