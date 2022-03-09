package com.example.smalltalk.database

import androidx.room.*


@Dao // definerer hva vi  kan gjøre med tabellene våre
interface UserDAO { // her defineres de funksjonene man vil ha tilgang til på en User

    @Query("SELECT * FROM user LIMIT 1")
    fun getLoggedInUser(): User

    @Query("SELECT * FROM user WHERE id = :userId LIMIT 1")
    fun getLoggedUserById(userId: String): User

    @Insert // dette er alt du trenger for å lagre en bruker
    fun addUser(user: User) // definert en funksjon som lagrer en bruker

    @Update
    fun updateUser(user: User)

    @Query ("DELETE FROM user")
    fun deleteAllUsers()

    @Delete
    fun deleteUser (user: User)




}


