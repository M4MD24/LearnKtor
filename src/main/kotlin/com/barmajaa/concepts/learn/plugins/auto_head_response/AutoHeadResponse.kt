package com.barmajaa.concepts.learn.plugins.auto_head_response

import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*

fun Application.configureAutoHeadResponse() {
    install(AutoHeadResponse)
}