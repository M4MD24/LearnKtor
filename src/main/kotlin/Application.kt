package com.barmajaa

import com.barmajaa.concepts.learn.plugins.resources.configureResources
import com.barmajaa.concepts.learn.plugins.rounting.configureRouting
import com.barmajaa.concepts.learn.plugins.serialization.configureSerialization
import com.barmajaa.concepts.learn.plugins.status_pages.configureStatusPages
import io.ktor.server.application.*

fun main(args : Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureResources()
    configureSerialization()
    configureStatusPages()
    configureRouting()
}