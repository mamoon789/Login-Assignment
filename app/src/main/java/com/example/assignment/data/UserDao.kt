package com.example.assignment.data

import androidx.room.*
import androidx.room.Dao
import com.example.assignment.data.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM User WHERE (phoneNumber = :phoneNumber AND password = :password)")
    suspend fun logInUser(phoneNumber: String, password: String): User?

    @Query("SELECT * FROM User WHERE phoneNumber = :phoneNumber")
    suspend fun checkUserExist(phoneNumber: String): User?

    @Query("SELECT * FROM User WHERE isLogin = :isLogin")
    suspend fun checkUserLoggedIn(isLogin: String = "1"): User?
}