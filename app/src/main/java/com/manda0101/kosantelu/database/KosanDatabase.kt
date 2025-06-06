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

        fun getInstance(context: Context): KosanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KosanDatabase::class.java,
                    "kosan_database"
                )
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(""" 
            CREATE TABLE IF NOT EXISTS `recycle_bin` (
                `kosan_id` INTEGER PRIMARY KEY NOT NULL, 
                `nama` TEXT NOT NULL, 
                `alamat` TEXT NOT NULL, 
                `harga` TEXT NOT NULL, 
                `fasilitas` TEXT NOT NULL
            )
        """)
            }
        }
    }
}