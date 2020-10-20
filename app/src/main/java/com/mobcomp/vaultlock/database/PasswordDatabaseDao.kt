package com.mobcomp.vaultlock.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PasswordDatabaseDao {

    @Insert
    fun insert(password: Password)

    @Update
    fun update(password: Password)

    @Query("SELECT * FROM password_table WHERE id = :key")
    fun get(key: Long): Password?

}