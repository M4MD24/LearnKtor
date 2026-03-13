package com.barmajaa.concepts.learn.plugins.server_sent_events

import io.ktor.server.application.*
import io.ktor.server.sse.*

fun Application.configureServerSentEvents() {
    install(SSE)
}