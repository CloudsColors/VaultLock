package com.mobcomp.vaultlock.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note (
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,
    @ColumnInfo(name = "note")
    var note : String = "",
    @ColumnInfo(name = "note_title")
    val noteTitle: String = ""
)