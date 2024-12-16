package com.example.traningcomposeapp.repository

import com.example.traningcomposeapp.data.dao.UserDao
import com.example.traningcomposeapp.data.entities.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)
    suspend fun getUserById(userId: Int): UserEntity? = userDao.getUserById(userId)
    suspend fun getUserByEmail(email: String): UserEntity? = userDao.getUserByEmail(email)
    suspend fun getUserByUsername(username: String): UserEntity? = userDao.getUserByUsername(username)
}