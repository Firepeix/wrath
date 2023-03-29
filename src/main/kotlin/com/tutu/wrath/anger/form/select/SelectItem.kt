package com.tutu.wrath.anger.form.select

import io.kvision.core.StringPair

interface SelectItem {
    fun toSelectItem(): StringPair
}