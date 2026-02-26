package com.barmajaa.concepts.learn.plugins.authentication.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respondText
import java.util.Date

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

fun generateToken(
    jwtConfig: JWTConfig,
    username: String
): String? = JWT.create()
    .withAudience(jwtConfig.audience)
    .withIssuer(jwtConfig.issuer)
    .withClaim(
        "username",
        username
    )
    .withExpiresAt(Date(System.currentTimeMillis() + jwtConfig.expiry))
    .sign(Algorithm.HMAC256(jwtConfig.secret))

data class JWTConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val expiry: Long
)