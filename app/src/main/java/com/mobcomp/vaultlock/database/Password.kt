package com.mobcomp.vaultlock.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_table")
data class Password (
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,
    @ColumnInfo(name = "password")
    var password : String = "",
    @ColumnInfo(name = "time_set")
    val timeCreated : Long = System.currentTimeMillis()
)