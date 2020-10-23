package com.mobcomp.vaultlock.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PasswordDatabaseDao {

    @Insert
    suspend fun insert(password: Password)

    @Update
    fun update(password: Password)

    @Query("SELECT * FROM password_table")
    suspend fun getPassword(): List<Password>

    @Query("DELETE FROM password_table")
    suspend fun dropTable()

}