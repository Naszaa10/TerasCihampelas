package com.nasza.terascihampelas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nasza.terascihampelas.model.Tenant
import com.nasza.terascihampelas.model.Review

@Database(entities = [Tenant::class, Review::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tenantDao(): TenantDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cihampelas_database"
                )
                    .fallbackToDestructiveMigration() // Tambahkan ini untuk sementara
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}



