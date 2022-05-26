package com.example.smalltalk.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity // ved å tægge den her så vi User klassen automatisk bli opptrettet som en Entity i Room og som en tabell i vår DB

data class User (
    val username: String,
    val fullName: String,
    @PrimaryKey val id: Int = 0 //Bytte fra 0 til String
    )

