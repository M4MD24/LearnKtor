package com.barmajaa.concepts.learn.plugins.resources

import io.ktor.server.application.*
import io.ktor.server.resources.*

fun Application.configureResources() {
    install(Resources)
}