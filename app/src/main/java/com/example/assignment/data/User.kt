package com.example.assignment.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey(autoGenerate = false)
    val phoneNumber: String,
    val name: String,
    val password: String,
    @ColumnInfo(defaultValue = "0")
    var isLogin: String = "0"
)
