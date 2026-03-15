package com.barmajaa.concepts.learn.plugins.call_logging

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import org.slf4j.event.Level

fun Application.configureCallLogging() {
    install(CallLogging) {
        level = Level.INFO
        filter { call ->
            call.request.path().startsWith("/hello")
        }
        format { call ->
            val userId = call.request.queryParameters["userId"] ?: "Unknown"
            "UserId: $userId, Method: ${call.request.httpMethod.value}, Code: ${call.response.status()!!.value}, Path: ${call.request.path()}"
        }
    }
}