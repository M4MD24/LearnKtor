package com.barmajaa

import com.barmajaa.concepts.learn.plugins.rounting.configureRouting
import io.ktor.server.application.*

fun main(args : Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}