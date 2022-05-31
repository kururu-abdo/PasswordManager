package com.kururu.password_manager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kururu.password_manager.data.dao.AccountDao
import com.kururu.password_manager.data.dao.AccountTypeDao
import com.kururu.password_manager.data.models.Account
import com.kururu.password_manager.data.models.AccountType

@Database(entities = [Account::class ,   AccountType::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountTypeDao(): AccountTypeDao
    abstract fun accountDao(): AccountDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "passwordmanager"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
