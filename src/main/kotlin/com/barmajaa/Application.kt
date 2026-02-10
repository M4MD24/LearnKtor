package com.barmajaa

import com.barmajaa.concepts.learn.plugins.authentication.basic_authentication.configureBasicAuthentication
import com.barmajaa.concepts.learn.plugins.authentication.digest_authentication.configureDigestAuthentication
import com.barmajaa.concepts.learn.plugins.auto_head_response.configureAutoHeadResponse
import com.barmajaa.concepts.learn.plugins.partial_content.configurePartialContent
import com.barmajaa.concepts.learn.plugins.rate_limit.configureRateLimiting
import com.barmajaa.concepts.learn.plugins.request_validation.configureRequestValidation
import com.barmajaa.concepts.learn.plugins.resources.configureResources
import com.barmajaa.concepts.learn.plugins.rounting.configureRouting
import com.barmajaa.concepts.learn.plugins.serialization.configureSerialization
import com.barmajaa.concepts.learn.plugins.status_pages.configureStatusPages
import io.ktor.server.netty.EngineMain.main
import io.ktor.server.application.*

fun main(args : Array<String>) = main(args)

fun Application.module() {
    configureResources()
    configureSerialization()
    configureStatusPages()
    configureRequestValidation()
    configureRateLimiting()
    configureAutoHeadResponse()
    configurePartialContent()
    configureAuthentications()
    configureRouting()
}

private fun Application.configureAuthentications() {
//    configureBasicAuthentication()
    configureDigestAuthentication()
}