package com.barmajaa.concepts.learn.plugins.postgresql.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

class DatabaseFactory {
    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5432/my_database"
        driverClassName = "org.postgresql.Driver"
        username = "admin"
        password = "secret"
        isReadOnly = false
        maximumPoolSize = 7
        transactionIsolation = "TRANSACTION_SERIALIZABLE"
    }

    private val dataSource = HikariDataSource(config)

    val database = Database.connect(dataSource)

    /*
    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/my_database",
        user = "admin",
        password = "secret",
        driver = "org.postgresql.Driver"
    )
    */
}