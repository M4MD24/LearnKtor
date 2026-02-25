package com.barmajaa.concepts.learn.plugins.authentication.bearer_authentication

import io.ktor.server.application.*
import io.ktor.server.auth.*

const val REALM = "Access protected routes"
val userDatabase: Map<String, String> = mapOf(
    "token1" to "user1",
    "token2" to "user2",
    "token3" to "user3",
    "token4" to "user4"
)

fun Application.configureBearerAuthentication() {
    install(Authentication) {
        bearer("bearer-authentication") {
            realm = REALM
            authenticate { tokenCredential ->
                val user = userDatabase[tokenCredential.token]
                if (!user.isNullOrBlank())
                    UserIdPrincipal(user)
                else
                    null
            }
        }
    }
}