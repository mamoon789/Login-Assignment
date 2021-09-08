package com.example.assignment.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment.data.User
import com.example.assignment.data.UserDao

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}