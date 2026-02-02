package com.barmajaa.concepts.learn.plugins.request_validation

fun String.isEmptyOrBlack() = this.isEmpty() || this.isBlank()
fun Float.isBelowTheAllowedLimit() = this < 0