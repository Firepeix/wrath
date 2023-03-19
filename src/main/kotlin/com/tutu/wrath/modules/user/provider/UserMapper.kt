package com.tutu.wrath.modules.user.provider

import com.tutu.wrath.modules.user.dto.UserListResponse
import com.tutu.wrath.modules.user.dto.UserResponse
import com.tutu.wrath.modules.user.model.User

class UserMapper {

    fun toUser(source: UserResponse): User {
        return User(source.id, source.name)
    }

    fun toUserList(source: UserListResponse): List<User> {
        return source.data.map(::toUser)
    }
}