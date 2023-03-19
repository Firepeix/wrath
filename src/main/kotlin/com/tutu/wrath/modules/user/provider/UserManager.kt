package com.tutu.wrath.modules.user.provider

import com.tutu.wrath.modules.user.usecases.UserUseCase
import com.tutu.wrath.util.Rocket

class UserManager(rocket: Rocket) {
    val mapper = UserMapper()
    val repository = UserRepository(rocket, mapper)
    val useCase = UserUseCase(repository)
}