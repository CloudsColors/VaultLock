package com.mobcomp.vaultlock.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_table")
data class Password (
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,
    @ColumnInfo(name = "password_f1")
    var password_f1 : String = "",
    @ColumnInfo(name = "password_f2")
    var password_f2 : String = "",
    @ColumnInfo(name = "password_f3")
    var password_f3 : String = "",
    @ColumnInfo(name = "password_f4")
    var password_f4 : String = ""
)