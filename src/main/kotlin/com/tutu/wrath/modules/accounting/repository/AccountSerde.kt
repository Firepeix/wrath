package com.tutu.wrath.modules.accounting.repository

import com.tutu.wrath.modules.accounting.models.Account
import com.tutu.wrath.modules.accounting.models.AccountType
import com.tutu.wrath.modules.user.models.User
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class AccountSerde : JsonContentPolymorphicSerializer<Account>(Account::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Account> {
        return when (element.jsonObject["type"]?.jsonPrimitive?.content) {
            AccountType.USER.name -> User.serializer()
            AccountType.ESTABLISHMENT.name -> User.serializer()
            else -> User.serializer()
        }
    }
}