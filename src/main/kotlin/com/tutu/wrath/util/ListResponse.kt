package com.tutu.wrath.util

import kotlinx.serialization.Serializable

@Serializable
open class ListResponse<T>(
    open val items: List<T>
)