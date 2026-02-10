package com.barmajaa.concepts.learn.plugins.authentication.basic_authentication

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserHashedTableAuth
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.UserPasswordCredential
import io.ktor.server.auth.basic
import io.ktor.util.getDigestFunction

fun Application.configureBasicAuthentication() {
    val hashedTable = createHashedUserTable()
    install(Authentication) {
        basic("basic-authentication") {
            validate { credential ->
                // validateUsingStaticCredentials(credential)

                validateWithHashedTable(hashedTable, credential)
            }
        }
    }
}

private fun validateWithHashedTable(
    hashedTable : UserHashedTableAuth,
    credential : UserPasswordCredential
) : UserIdPrincipal? {
    return hashedTable.authenticate(credential)
}

private fun createHashedUserTable() : UserHashedTableAuth {
    val encryptionAlgorithmName = "SHA-256"
    val digestFunction = getDigestFunction(encryptionAlgorithmName) {
        "ktor${it.length}"
    }
    return UserHashedTableAuth(
        digester = digestFunction,
        table = mapOf(
            "admin" to digestFunction("password"),
            "user" to digestFunction("12345678")
        )
    )
}

private fun validateUsingStaticCredentials(
    credential : UserPasswordCredential
) : UserIdPrincipal? {
    val username = credential.name
    val password = credential.password

    return if (username == "admin" && password == "password")
        UserIdPrincipal(username)
    else
        null
}
