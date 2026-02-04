package com.barmajaa.concepts.learn.plugins.status_pages

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.TooManyRequests) { call, cause ->
            val retryAfter = call.response.headers["Retry-After"]
            call.respondText(text = "$cause, Wait $retryAfter second")
        }

        exception<Throwable> { call, cause ->
            call.respondText(
                text = "500: $cause",
                status = HttpStatusCode.InternalServerError
            )
        }

        status(HttpStatusCode.Unauthorized) { call, cause ->
            call.respondText(
                text = cause.toString(),
                status = HttpStatusCode.Unauthorized
            )
        }

        status(HttpStatusCode.BadRequest) { call, cause ->
            call.respondText(
                text = cause.toString(),
                status = HttpStatusCode.BadRequest
            )
        }

        statusFile(
            HttpStatusCode.BadRequest,
            HttpStatusCode.NotFound,
            filePattern = "errors/error#.html"
        )
    }
}