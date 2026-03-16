package com.barmajaa.concepts.learn.plugins.databases.nosql.mongodb.data

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class UserEntity(
    @param:BsonId
    val id: String = ObjectId().toString(),
    val name: String,
    val email: String,
    val profession: String,
    val age: Int,
    val country: String
)