package com.barmajaa.concepts.learn.plugins.partial_content

import io.ktor.server.application.*
import io.ktor.server.plugins.partialcontent.*

fun Application.configurePartialContent() {
    install(PartialContent)
}