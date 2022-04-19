package com.app.repositories.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.repositories.local.dao.*
import com.app.repositories.local.entity.*
import com.app.repositories.utils.CustomTypeConverter
import com.app.repositories.utils.DateTimeTypeConverter
import com.app.repositories.utils.ListTypeConverter

@Database(
    entities = [UserEntity::class],
    version = 1
)
@TypeConverters(DateTimeTypeConverter::class, ListTypeConverter::class)
abstract class GithubRepositoryDB : RoomDatabase() {
    abstract fun commonDao(): CommonDao
}