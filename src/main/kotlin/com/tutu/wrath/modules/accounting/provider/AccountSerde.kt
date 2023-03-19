package com.tutu.wrath.modules.accounting.provider

import com.tutu.wrath.modules.accounting.dto.AccountResponse
import com.tutu.wrath.modules.accounting.dto.UnknownAccountTypeResponse
import com.tutu.wrath.modules.accounting.models.AccountType
import com.tutu.wrath.modules.user.dto.UserResponse
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class AccountSerde : JsonContentPolymorphicSerializer<AccountResponse>(AccountResponse::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<AccountResponse> {
        return when (element.jsonObject["type"]?.jsonPrimitive?.content) {
            AccountType.USER.name -> UserResponse.serializer()
            AccountType.ESTABLISHMENT.name -> UserResponse.serializer()
            else -> UnknownAccountTypeResponse.serializer()
        }
    }
}