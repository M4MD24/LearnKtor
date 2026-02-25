package com.barmajaa.concepts.learn.plugins.authentication.sessions_authentication

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

fun Application.configureSessionsAuthentication() {
    install(Authentication) {
        session<UserSession>("sessions-authentication") {
            validate { session ->
                session
            }
            challenge {
                call.respondText(
                    "Unauthorized!",
                    status = HttpStatusCode.Unauthorized
                )
            }
        }
    }
}