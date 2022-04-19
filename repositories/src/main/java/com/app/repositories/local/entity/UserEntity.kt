package com.app.repositories.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity
data class UserEntity(
    @PrimaryKey val userId: Long = -1,
    val firstName: String? = "",
    val lastName: String? = "",
    val email: String,
)