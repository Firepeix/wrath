package com.tutu.wrath.modules.expense.provider

import com.tutu.wrath.modules.accounting.provider.AccountMapper
import com.tutu.wrath.modules.card.shared.provider.CardMapper
import com.tutu.wrath.modules.expense.dto.ExpenseResponse
import com.tutu.wrath.modules.expense.model.Expense
import com.tutu.wrath.util.Chrono
import com.tutu.wrath.util.Money

class ExpenseMapper(private val cardMapper: CardMapper, private val accountMapper: AccountMapper) {

    fun toExpense(source: ExpenseResponse): Expense {
        return Expense(
            id = source.id,
            payed = source.payed,
            name = source.name,
            amount = Money(source.amount),
            date = Chrono(source.date),
            type = source.type,
            card = source.card?.run { cardMapper.toCard(this) },
            source = source.source?.run { accountMapper.toAccount(this) },
            beneficiary = source.beneficiary?.run { accountMapper.toAccount(this) },
            installments = source.installments?.run { toInstallments(this) }
        )
    }

    private fun toInstallments(source: ExpenseResponse.InstallmentsResponse): Expense.Installments {
        return Expense.Installments(current = source.current, total = source.total)
    }
}