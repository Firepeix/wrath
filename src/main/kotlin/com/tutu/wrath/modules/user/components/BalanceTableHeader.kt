package com.tutu.wrath.modules.user.components

import com.tutu.wrath.anger.form.select.Select
import com.tutu.wrath.anger.form.select.select
import com.tutu.wrath.modules.user.model.User
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.state.ObservableValue


fun Container.balanceTableHeader(userId: ObservableValue<User?>, users: List<User>): Select<User> {
    div("Divisão", className = "w-full bg-neutral p-3.5 pb-3 leading-none text-center font-bold rounded-t")
    return select(userId, Select.Properties(users), Select.Attributes(header = true))
}