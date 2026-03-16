package com.barmajaa.concepts.learn.plugins.databases.nosql.mongodb.data

class UsersDataSource {
    private val database = DatabaseFactory.database
    private val usersCollection = database.getCollection<UserEntity>("Users")
    suspend fun insertOneUser(entity: UserEntity): Boolean {
        return usersCollection.insertOne(entity).wasAcknowledged()
    }

    suspend fun insertMultipleUsers(entities: List<UserEntity>): Boolean {
        return usersCollection.insertMany(entities).wasAcknowledged()
    }
}