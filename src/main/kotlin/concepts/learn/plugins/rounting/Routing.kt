package com.barmajaa.concepts.learn.plugins.rounting

import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(RoutingRoot) {
        route("/", HttpMethod.Get) {
            handle {
                call.respondText("Hello Ktor Again!")
            }
        }
    }

    routing {
        get("/") {
            call.respondText("Hello Ktor!")
        }

        get("profile/{profileID}/{skills}") {
            val profileID = call.parameters["profileID"]
            val skills = call.parameters["skills"]
            val skillList = getSkills(skills!!)
            call.respondText("Profile ID: $profileID\nSkills:\n${skillList.joinToString("\n") { "\t- $it" }}")
        }

        get(Regex(".+/test")) {
            call.respondText("Test Ktor!")
        }

        get(Regex("api/(?<apiVersion>v[1-4])")) {
            val apiVersion = call.pathParameters["apiVersion"]
            call.respondText("Api Version is $apiVersion")
        }
    }
}

private fun getSkills(skills : String) = skills.split(",")