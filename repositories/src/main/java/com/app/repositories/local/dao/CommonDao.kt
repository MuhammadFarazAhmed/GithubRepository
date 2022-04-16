package com.app.repositories.local.dao

import androidx.room.Dao
import androidx.room.Transaction
import com.app.repositories.local.GithubRepositoryDB

@Dao
abstract class CommonDao(
    private val db: GithubRepositoryDB
) {

}