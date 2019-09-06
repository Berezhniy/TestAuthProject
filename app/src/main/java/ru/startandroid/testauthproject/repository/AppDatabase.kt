package ru.startandroid.testauthproject.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.startandroid.testauthproject.repository.dao.UserDao
import ru.startandroid.testauthproject.repository.entity.UserEntity

@Database(entities = [(UserEntity::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}