package com.barmajaa.concepts.learn.plugins.custom_plugin

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.BaseApplicationPlugin
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.response.header
import io.ktor.util.AttributeKey

fun Application.configureCustomPlugin() {
    install(CustomHeader) {
        headerKey = "X-Powered-By"
        headerValue = "Ktor Custom Plugin"
    }
}

class CustomHeader(configuration: Configuration) {
    private val headerKey = configuration.headerKey
    private val headerValue = configuration.headerValue

    class Configuration {
        var headerKey = "X-Custom-Header"
        var headerValue = "Default Value"
    }

    companion object Plugin : BaseApplicationPlugin<ApplicationCallPipeline, Configuration, CustomHeader> {
        override val key: AttributeKey<CustomHeader> = AttributeKey<CustomHeader>("CustomHeader")
        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: Configuration.() -> Unit
        ): CustomHeader {
            val configuration = Configuration().apply(configure)
            val plugin = CustomHeader(configuration)
            pipeline.intercept(ApplicationCallPipeline.Plugins) {
                call.response.header(
                    plugin.headerKey,
                    plugin.headerValue
                )
            }
            return plugin
        }
    }
}