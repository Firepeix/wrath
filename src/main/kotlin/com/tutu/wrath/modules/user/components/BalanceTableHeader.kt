package com.tutu.wrath.modules.user.components

import com.tutu.wrath.anger.form.select.SelectItem
import com.tutu.wrath.anger.form.select.select
import com.tutu.wrath.modules.user.model.User
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.html.div
import io.kvision.state.ObservableValue


fun Container.balanceTableHeader(userId: ObservableValue<User?>, users: ObservableValue<List<User>>) {
    div("Divisão", className = "w-full bg-neutral p-3 text-center font-bold rounded-t")
    select(userId, users, asHeader = true)
}