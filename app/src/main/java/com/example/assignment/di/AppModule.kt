package com.example.assignment.di

import android.app.Application
import androidx.room.Room
import com.example.assignment.data.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Application) =
        Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
            .fallbackToDestructiveMigration()
            .build()
}