package com.tutu.wrath.modules.user.model

import com.tutu.wrath.anger.form.select.SelectItem
import com.tutu.wrath.modules.accounting.models.Account
import io.kvision.core.StringPair

data class User(val id: String, val name: String) : Account, SelectItem {
    override fun getName() = name

    override fun toSelectItem(): StringPair = id to name
}