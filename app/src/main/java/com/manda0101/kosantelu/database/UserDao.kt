package com.manda0101.kosantelu.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.manda0101.kosantelu.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?
}