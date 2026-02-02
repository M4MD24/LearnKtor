package com.barmajaa.concepts.learn.plugins.request_validation

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<String> { bodyText ->
            if (bodyText.isEmpty())
                ValidationResult.Invalid("Empty!")
            else if (bodyText.isBlank())
                ValidationResult.Invalid("Blank!")
            else
                ValidationResult.Valid
        }
    }
}