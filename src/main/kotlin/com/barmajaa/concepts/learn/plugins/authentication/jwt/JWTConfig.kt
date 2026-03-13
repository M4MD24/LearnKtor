package com.barmajaa.concepts.learn.plugins.authentication.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

data class JWTConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val expiry: Long
)

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