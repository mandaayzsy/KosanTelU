package com.manda0101.kosantelu.database

import com.manda0101.kosantelu.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }
}