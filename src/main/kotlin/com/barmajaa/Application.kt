package com.barmajaa

import com.barmajaa.concepts.learn.plugins.authentication.jwt.JWTConfig
import com.barmajaa.concepts.learn.plugins.authentication.jwt.configureJWTAuthentication
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
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain.main

fun main(args: Array<String>) = main(args)

fun Application.module() {
    val jwtConfig = getJWTConfig()
    configureResources()
    configureSerialization()
    configureStatusPages()
    configureRequestValidation()
    configureRateLimiting()
    configureAutoHeadResponse()
    configurePartialContent()
    configureAuthentications(jwtConfig)
    configureRouting(jwtConfig)
}

private fun Application.configureAuthentications(jwtConfig: JWTConfig) {
//    configureBasicAuthentication()
//    configureDigestAuthentication()
//    configureBearerAuthentication()
//    sessionAuthentication()
    configureJWTAuthentication(jwtConfig)
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