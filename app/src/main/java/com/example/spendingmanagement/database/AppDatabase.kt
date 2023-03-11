package com.example.spendingmanagement.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spendingmanagement.model.Item

@Database(entities = [Item::class] , version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): Dao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDataBase(): AppDatabase {
            synchronized(AppDatabase::class) {
                if (INSTANCE == null) {
                    throw RuntimeException("Please create instance from application")
                }
                return INSTANCE!!
            }
        }

        fun createInstance(context: Context) {
            synchronized(AppDatabase::class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "MyDB"
                    ).build()
                }
            }
        }

    }

}