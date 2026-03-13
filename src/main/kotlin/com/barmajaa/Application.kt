package com.barmajaa

import com.barmajaa.concepts.learn.plugins.authentication.jwt.JWTConfig
import com.barmajaa.concepts.learn.plugins.authentication.open.configureJWTAuthentication
import com.barmajaa.concepts.learn.plugins.authentication.sessions.configureSessions
import com.barmajaa.concepts.learn.plugins.authentication.sessions.configureSessionsAuthentication
import com.barmajaa.concepts.learn.plugins.auto_head_response.configureAutoHeadResponse
import com.barmajaa.concepts.learn.plugins.partial_content.configurePartialContent
import com.barmajaa.concepts.learn.plugins.rate_limit.configureRateLimiting
import com.barmajaa.concepts.learn.plugins.request_validation.configureRequestValidation
import com.barmajaa.concepts.learn.plugins.resources.configureResources
import com.barmajaa.concepts.learn.plugins.routing.configureRouting
import com.barmajaa.concepts.learn.plugins.serialization.configureSerialization
import com.barmajaa.concepts.learn.plugins.status_pages.configureStatusPages
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain.main
import kotlinx.serialization.json.Json

fun main(args: Array<String>) = main(args)

fun Application.module() {
    val jwtConfig = getJWTConfig()
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    configureResources()
    configureSerialization()
    configureStatusPages()
    configureRequestValidation()
    configureRateLimiting()
    configureAutoHeadResponse()
    configurePartialContent()
    configureAuthentications(
        jwtConfig,
        httpClient
    )
    configureRouting(
        jwtConfig,
        httpClient
    )
}

private fun Application.configureAuthentications(
    jwtConfig: JWTConfig,
    httpClient: HttpClient
) {
//    configureBasicAuthentication()
//    configureDigestAuthentication()
//    configureBearerAuthentication()
//    sessionAuthentication()
//    configureJWTAuthentication(jwtConfig)
    configureJWTAuthentication(
        jwtConfig,
        httpClient
    )
}

private fun Application.getJWTConfig(): JWTConfig {
    val jwt = environment.config.config("ktor.jwt")
    val secret = jwt.property("secret").getString()
    val issuer = jwt.property("issuer").getString()
    val audience = jwt.property("audience").getString()
    val realm = jwt.property("realm").getString()
    val expiry = jwt.property("expiry").getString().toLong()
    return JWTConfig(secret, issuer, audience, realm, expiry)
}

private fun Application.sessionAuthentication() {
    configureSessions()
    configureSessionsAuthentication()
}