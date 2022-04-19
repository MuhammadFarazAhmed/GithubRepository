package com.app.repositories.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.repositories.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllUsers(userList: List<UserEntity>)

    @Delete
    suspend fun deleteUserEntity(user: UserEntity)

    @Query("SELECT * from userentity")
    fun observeAllUserEntitys(): LiveData<List<UserEntity>>

    @Query("SELECT * from userentity where userId = :id")
    fun observeUserEntityBId(id: Int): LiveData<UserEntity>

    @Query("SELECT COUNT(*) FROM userentity")
    suspend fun getCount(): Int
}