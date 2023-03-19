package com.tutu.wrath.modules.user.components

import com.tutu.wrath.anger.form.select.select
import io.kvision.core.Container
import io.kvision.core.StringPair
import io.kvision.html.div
import io.kvision.state.ObservableValue


fun Container.balanceTableHeader(userId: ObservableValue<String?>, users: ObservableValue<List<StringPair>>) {
    div("Divisão", className = "w-full bg-neutral p-4 text-center font-bold rounded-t")
    select(userId, users, asHeader = true)
}