package com.barmajaa.concepts.learn.plugins.shut_down_url

import io.ktor.server.application.*
import io.ktor.server.engine.*

fun Application.configureShutDownURL() {
    install(ShutDownUrl.ApplicationCallPlugin){
        shutDownUrl = "/shutdown"
        exitCodeSupplier = {0}
    }
}