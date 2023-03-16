package com.tutu.wrath.util

import io.kvision.rest.RestClient
import io.kvision.rest.call
import kotlinx.coroutines.await

class Rocket {
    val client = RestClient() {
        baseUrl = "http://localhost:3003/"
    }

   suspend inline fun <reified T: Any> get(path: String) : Result<T> {
       val response = client.call<T>(path)
       return runCatching { response.await() }
   }
}