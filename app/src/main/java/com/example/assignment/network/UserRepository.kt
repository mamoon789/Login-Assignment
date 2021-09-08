package com.example.assignment.network

import com.example.assignment.data.User
import com.example.assignment.data.UserDatabase
import javax.inject.Inject

class UserRepository @Inject constructor(userDatabase: UserDatabase) {

    private val userDao = userDatabase.getUserDao()

    suspend fun insertUser(user: User) =
        userDao.insertUser(user)

    suspend fun updateUser(user: User) =
        userDao.updateUser(user)

    suspend fun logInUser(phoneNumber: String, password: String): User? =
        userDao.logInUser(phoneNumber, password)

    suspend fun checkUserExist(phoneNumber: String): User? =
        userDao.checkUserExist(phoneNumber)

    suspend fun checkUserLoggedIn(): User? =
        userDao.checkUserLoggedIn()
}