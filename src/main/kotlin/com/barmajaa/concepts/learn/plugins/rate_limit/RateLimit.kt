package com.barmajaa.concepts.learn.plugins.rate_limit

import io.ktor.server.application.*
import io.ktor.server.plugins.ratelimit.*
import kotlin.time.Duration.Companion.seconds

const val PRIVATE_LIMIT = 5
const val PROTECTED_LIMIT = 7
private const val GLOBAL_LIMIT = 10
var REQUEST_WEIGHT = 1
private val refillPeriod = 60.seconds
fun Application.configureRateLimiting() {
    install(RateLimit) {
        global {
            rateLimiter(
                limit = GLOBAL_LIMIT,
                refillPeriod = refillPeriod
            )
        }
        register(RateLimitName("private")) {
            rateLimiter(
                limit = PRIVATE_LIMIT,
                refillPeriod = refillPeriod
            )
        }
        register(RateLimitName("protected")) {
            rateLimiter(
                limit = PROTECTED_LIMIT,
                refillPeriod = refillPeriod
            )
            requestKey { call ->
                call.request.queryParameters["type"] ?: ""
            }
            requestWeight { call, key ->
                val requestWeight = when (key) {
                    "admin" -> 2
                    else    -> REQUEST_WEIGHT
                }
                REQUEST_WEIGHT = requestWeight
                requestWeight
            }
        }
    }
}