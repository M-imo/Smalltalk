package com.example.smalltalk

import java.sql.Timestamp
import java.util.*

data class ChatObject(

    val userId: String,
    val message: String,
    val username: String,
    val timestamp: Long
)
