package com.barmajaa.concepts.learn.plugins.rounting

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

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

        get<Profiles> {
            val sort = it.sort
            call.respondText("Sort order: $sort")
        }

        delete<Profiles.Profile> {
            val sort = it.parent.sort
            val profileID = it.id
            call.respondText("Account ($profileID) has been deleted, According to the ($sort) order.")
        }
    }
}
@Serializable
@Resource("profiles")
class Profiles(val sort : String? = "new") {
    @Serializable
    @Resource("{id}")
    data class Profile(
        val parent : Profiles = Profiles(),
        val id : String
    )
}

private fun getSkills(skills : String) = skills.split(",")