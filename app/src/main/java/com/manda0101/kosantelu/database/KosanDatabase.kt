package com.manda0101.kosantelu.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manda0101.kosantelu.model.Kosan
import com.manda0101.kosantelu.model.RecycleBin

@Database(entities = [Kosan::class, RecycleBin::class], version = 2, exportSchema = false)
abstract class KosanDatabase : RoomDatabase() {

    abstract fun kosanDao(): KosanDao
    abstract fun recycleBinDao(): RecycleBinDao

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
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Migration to add the recycle_bin table
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create recycle_bin table in version 2
                db.execSQL(""" 
                    CREATE TABLE IF NOT EXISTS `recycle_bin` (
                        `kosan_id` INTEGER PRIMARY KEY NOT NULL, 
                        `nama` TEXT, 
                        `alamat` TEXT, 
                        `harga` TEXT, 
                        `fasilitas` TEXT
                    )
                """)
            }
        }
    }
}