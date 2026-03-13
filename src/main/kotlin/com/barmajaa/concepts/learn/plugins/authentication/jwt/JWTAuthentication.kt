package com.barmajaa.concepts.learn.plugins.authentication.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureJWTAuthentication(jwtConfig: JWTConfig) {
    install(Authentication) {
        jwt("jwt-authentication") {
            realm = jwtConfig.realm
            val jwtVerifier = JWT
                .require(Algorithm.HMAC256(jwtConfig.secret))
                .withAudience(jwtConfig.audience)
                .withIssuer(jwtConfig.issuer)
                .build()
            verifier(jwtVerifier)
            validate { jwtCredential ->
                val payload = jwtCredential.payload
                val username = payload.getClaim("username").asString()
                if (!username.isNullOrBlank())
                    JWTPrincipal(payload)
                else
                    null
            }
            challenge { defaultSchema, realm ->
                call.respondText(
                    "Token is not valid or has expired!",
                    status = HttpStatusCode.Unauthorized
                )
            }
        }
    }
}