package com.barmajaa.concepts.learn.plugins.rounting

import io.ktor.http.*
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.request.receiveChannel
import io.ktor.server.request.receiveMultipart
import io.ktor.server.request.receiveNullable
import io.ktor.server.request.receiveParameters
import io.ktor.server.request.receiveText
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import io.ktor.utils.io.readRemaining
import io.ktor.utils.io.readText
import kotlinx.serialization.Serializable
import java.io.File

fun Application.configureRouting() {
    install(RoutingRoot) {
        // GET /
        // Example: http://localhost:8080/
        route("/", HttpMethod.Get) {
            handle {
                call.respondText("Hello Ktor Again!")
            }
        }
    }

    routing {
        // GET /
        // Example: http://localhost:8080/
        get {
            call.respondText("Hello Ktor!")
        }

        profileRoutes()

        typeSafeRoutes()

        dynamicRoutes()

        accountRoutes()

        messageRoutes()

        uploadRoutes()

        productRoutes()

        multipartData()

        statusPages()

        requestValidation()
    }
}

private fun Route.errorHandling() {
    // GET /products/pens
    // Example: http://localhost:8080/products/pens
    get("products/pens") {
        throw Exception("Database failed to initialize")
    }
    // GET /products/pens
    // Example: http://localhost:8080/products/pencils
    get("products/pencils") {
        call.respond(HttpStatusCode.Unauthorized)
    }
    // GET /products/rulers
    // Example: http://localhost:8080/products/rulers
    get("products/rulers") {
        val statuses = listOf(
            HttpStatusCode.BadRequest,
            HttpStatusCode.NotFound
        )
        val randomStatus = statuses.random()
        call.respond(randomStatus)
    }
}
@Serializable
data class Thing(
    val name : String,
    val category : String,
    val price : Float
)

private fun Route.requestValidation() {
    // POST /products/pens
    // Example: http://localhost:8080/products/pencils with body (raw (Text)): ""
    // Example: http://localhost:8080/products/pencils with body (raw (Text)): " "
    // Example: http://localhost:8080/products/pencils with body (raw (Text)): "Hello"
    post("products/pens") {
        val message = call.receive<String>()
        call.respondText(message)
    }
    // POST products/things
    // Example: http://localhost:8080/products/things with body (raw (JSON)): {"name":"","category": " ","price": -1}
    // Example: http://localhost:8080/products/things with body (raw (JSON)): {"name":"Asus Laptop","category": " ","price": -1}
    // Example: http://localhost:8080/products/things with body (raw (JSON)): {"name":"Asus Laptop","category": "Electronics","price": -1}
    // Example: http://localhost:8080/products/things with body (raw (JSON)): {"name":"Asus Laptop","category": "Electronics","price": 999.99}
    post("products/things") {
        val thing = call.receive<Thing>()
        call.respond(thing)
    }
}

private fun Route.statusPages() {
    errorHandling()
}

private fun Route.multipartData() {
    // POST /things
    // Example: http://localhost:8080/things with body (form-data): name=Mohamed&Logo=Select Image (src\main\kotlin\concepts\learn\plugins\resources\images\image.png)&job=Android Apps Developer
    post("things") {
        val things = call.receiveMultipart(
            formFieldLimit = 1024 * 1024 * 60
        )
        val fields = mutableMapOf<String, MutableList<String>>()
        things.forEachPart { thing ->
            val key = thing.name ?: return@forEachPart
            when (thing) {
                is PartData.FormItem -> {
                    fields.getOrPut(key) { mutableListOf() }.add(thing.value)
                }

                is PartData.FileItem -> {
                    val fileName = thing.originalFileName ?: return@forEachPart
                    fields.getOrPut(key) { mutableListOf() }.add(fileName)
                    val file = File("uploads/things/$fileName").apply {
                        parentFile?.mkdirs()
                    }
                    thing.provider().copyAndClose(file.writeChannel())
                }

                else                 -> {}
            }
            thing.dispose
        }
        call.respond("Form fields: $fields")
    }
}
@Serializable
private data class Product(
    val name : String,
    val category : String,
    val price : Float,
)

private fun Route.productRoutes() {
    // POST /product
    // Example: http://localhost:8080/product with body (binary): Select Image (src\main\kotlin\concepts\learn\plugins\resources\images\image.png)
    post("product") {
        val product = call.receiveNullable<Product>() ?: return@post /* call.respond(HttpStatusCode.BadRequest)*/
        call.respond(product)
    }
}

private fun Route.uploadRoutes() {
    // POST /upload
    // Example: http://localhost:8080/upload with body (binary): Select Image (src\main\kotlin\concepts\learn\plugins\resources\images\image.png)
    post("upload") {
        val file = File("uploads/image3.png").apply {
            parentFile?.mkdirs()
        }
        // Solution 1 (image1)
        /*
        val byteArray = call.receive<ByteArray>()
        file.writeBytes(byteArray)
        */
        // Solution 2 (image2)
        /*
        val stream = call.receiveStream()
        FileOutputStream(file).use {
            stream.copyTo(
                out = it,
                bufferSize = 16 * 1024
            )
        }
        */
        // Solution 3 (image3)
        val channel = call.receiveChannel()
        channel.copyAndClose(file.writeChannel())
        call.respondText("The file was uploaded successfully.")
    }
}

private fun Route.messageRoutes() {
    // POST /great
    // Example: http://localhost:8080/great with body (raw (Text)): Mohamed
    post("great") {
        val name = call.receiveText()
        call.respondText("Hello $name")
    }
    // POST /channel
    // Example: http://localhost:8080/channel with body (raw (Text)): Hello Android Apps Developers
    post("channel") {
        val channel = call.receiveChannel()
        val text = channel.readRemaining().readText()
        call.respondText(text)
    }
}

private fun getSkills(skills : String) = skills.split(",")

private fun Route.profileRoutes() {
    // GET /profile/{profileID}/{skills}
    // Example: http://localhost:8080/profile/123/kotlin,java,ktor
    get("profile/{profileID}/{skills}") {
        val profileID = call.parameters["profileID"]
        val skills = call.parameters["skills"]
        val skillList = getSkills(skills!!)
        call.respondText(
            "Profile ID: $profileID\nSkills:\n" +
                    skillList.joinToString("\n") { "\t- $it" }
        )
    }
}

private fun Route.typeSafeRoutes() {
    // GET /profiles?sort=new
    // Example: http://localhost:8080/profiles?sort=new
    get<Profiles> {
        val sort = it.sort
        call.respondText("Sort order: $sort")
    }
    // DELETE /profiles/{id}
    // Example: http://localhost:8080/profiles/123
    delete<Profiles.Profile> {
        val sort = it.parent.sort
        val profileID = it.id
        call.respondText("Account ($profileID) has been deleted, According to the ($sort) order.")
    }
}
@Serializable
@Resource("profiles")
private class Profiles(val sort : String? = "new") {
    @Serializable
    @Resource("{id}")
    data class Profile(
        val parent : Profiles = Profiles(),
        val id : String
    )
}

private fun Route.dynamicRoutes() {
    // GET /{anything}/test
    // Example: http://localhost:8080/hello/test
    get(Regex(".+/test")) {
        call.respondText("Test Ktor!")
    }
    // GET /api/{v1|v2|v3|v4}
    // Example: http://localhost:8080/api/v2
    get(Regex("api/(?<apiVersion>v[1-4])")) {
        val apiVersion = call.pathParameters["apiVersion"]
        call.respondText("Api Version is $apiVersion")
    }
}

private data class Account(
    val id : Int? = null,
    val username : String? = null,
    val password : String? = null
) {
    companion object {
        fun fromCSV(
            csv : String,
            delimiters : String
        ) : Account {
            val parts = csv.split(delimiters)
            val id = parts.getOrNull(0)?.toIntOrNull()
            val username = parts.getOrNull(1)
            val password = parts.getOrNull(2)
            return Account(
                id = id,
                username = username,
                password = password
            )
        }
    }

    fun getAllInformation() = "\n\t- ID: $id" +
            "\n\t- Username: @$username" +
            "\n\t- Password: $password"
}

private fun Route.accountRoutes() {
    route("accounts") {
        // GET /accounts
        // Example: http://localhost:8080/accounts
        get {
            call.respondText("Get All Accounts")
        }
        // GET /accounts/{id}
        // Example: http://localhost:8080/accounts/142
        get("{id}") {
            val id = call.pathParameters["id"]?.toInt()
            val account = Account(id = id)
            call.respondText("User ID: ${account.id}")
        }
        // POST /accounts/{id},{username},{password}
        // Example: http://localhost:8080/accounts/142,m4md24,142142
        post("{data}") {
            val data = call.pathParameters["data"]
            val delimiters = ","
            val account = Account.fromCSV(
                csv = data!!,
                delimiters = delimiters
            )
            call.respondText("Added Account:" + account.getAllInformation())
        }
        // PATCH /accounts/{id}
        // Example: http://localhost:8080/accounts/142 with body (x-www-from-urlencoded): username=m4md24
        patch("{id}") {
            val id = call.parameters["id"]
            val params = call.receiveParameters()
            val username = params["username"]
            call.respondText("Updated Account $id with new username: $username")
        }

        route("auth") {
            // POST /accounts/auth/login
            // Example: http://localhost:8080/accounts/auth/login with body (x-www-from-urlencoded): username=m4md24&password=142142
            post("login") {
                val params = call.receiveParameters()
                val username = params["username"]
                val password = params["password"]
                call.respondText("Logged in as $username")
            }
            // POST /accounts/auth/signup
            // Example: http://localhost:8080/accounts/auth/signup with body (x-www-from-urlencoded): username=m4md24&password=142142
            post("signup") {
                val params = call.receiveParameters()
                val username = params["username"]
                val password = params["password"]
                call.respondText("Account created for $username")
            }
        }
    }
}