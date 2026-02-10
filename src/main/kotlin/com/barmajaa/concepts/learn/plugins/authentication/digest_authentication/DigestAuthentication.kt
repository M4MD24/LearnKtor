package com.barmajaa.concepts.learn.plugins.authentication.digest_authentication

import io.ktor.server.application.*
import io.ktor.server.auth.*
import java.security.MessageDigest

const val REALM = "Access protected routes"
val userTable : Map<String, ByteArray> = mapOf(
    "admin" to getMD5Digest("admin:${REALM}:password"),
    "user" to getMD5Digest("user:${REALM}:12345678")
)

private fun getMD5Digest(value : String) : ByteArray {
    val encryptionAlgorithmName = "MD5"
    return MessageDigest
        .getInstance(encryptionAlgorithmName)
        .digest(value.toByteArray())
}

fun Application.configureDigestAuthentication() {
    install(Authentication) {
        digest("digest-authentication") {
            realm = REALM
            digestProvider { username, realm ->
                userTable[username]
            }
            validate { credential ->
                if (credential.userName.isNotBlank())
                    UserIdPrincipal(credential.userName)
                else
                    null
            }
        }
    }
}