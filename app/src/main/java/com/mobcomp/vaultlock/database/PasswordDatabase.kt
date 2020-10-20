package com.mobcomp.vaultlock.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Password::class], version = 1, exportSchema = false)
abstract class PasswordDatabase : RoomDatabase(){

    abstract val sleepDatabaseDao: PasswordDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: PasswordDatabase? = null
        @InternalCoroutinesApi
        fun getInstance(context: Context): PasswordDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PasswordDatabase::class.java,
                        "sleep_history_database")
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}