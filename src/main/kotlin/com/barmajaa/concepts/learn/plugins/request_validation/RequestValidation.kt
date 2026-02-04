package com.barmajaa.concepts.learn.plugins.request_validation

import com.barmajaa.concepts.learn.plugins.rounting.Thing
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

        validate<Thing> {
            if (it.name.isEmptyOrBlack())
                ValidationResult.Invalid("The Name is Empty or Black!")
            else if (it.category.isEmptyOrBlack())
                ValidationResult.Invalid("The Category is Empty or Black!")
            else if (it.price.isBelowTheAllowedLimit())
                ValidationResult.Invalid("The Price is Below the allowed limit!")
            else
                ValidationResult.Valid
        }
    }
}