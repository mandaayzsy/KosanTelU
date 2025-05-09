package com.manda0101.kosantelu.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.manda0101.kosantelu.model.Kosan

@Database(entities = [Kosan::class], version = 1, exportSchema = false)
abstract class KosanDatabase : RoomDatabase() {

    abstract fun kosanDao(): KosanDao

    companion object {

        @Volatile
        private var INSTANCE: KosanDatabase? = null

        // Function to get the instance of the database
        fun getInstance(context: Context): KosanDatabase {
            // Return existing instance or create a new one
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KosanDatabase::class.java,
                    "kosan_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}