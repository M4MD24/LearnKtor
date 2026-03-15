package com.barmajaa.concepts.learn.plugins.databases.sql.postgresql.data

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class UserDataSource(database: Database) {
    object Users : Table("users") {
        val id = integer(name = "id").autoIncrement()
        val name = varchar(
            name = "name",
            length = 50
        )
        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Users)
            Users.insert {
                it[name] = "Mohamed Sadawy Thabet"
            }
        }
    }
}