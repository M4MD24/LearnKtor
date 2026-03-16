package com.barmajaa.concepts.learn.plugins.databases.nosql.mongodb.data

import com.mongodb.kotlin.client.coroutine.MongoClient

object DatabaseFactory {
    private val connectionString = System.getenv("MONGO_DB_URI")

    val database = MongoClient
        .create(connectionString)
        .getDatabase("LearnKtor")
}