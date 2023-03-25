package com.tutu.wrath.modules.expense.provider

import com.tutu.wrath.modules.accounting.provider.AccountMapper
import com.tutu.wrath.modules.card.shared.provider.CardMapper
import com.tutu.wrath.util.Rocket

class ExpenseManager(rocket: Rocket, accountMapper: AccountMapper, cardMapper: CardMapper) {
    val mapper = ExpenseMapper(cardMapper, accountMapper)
}