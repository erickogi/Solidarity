package com.dev.common.data.local

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dev.common.data.local.daos.ProfileDao
import com.dev.common.models.Converters
import com.agriclinic.common.models.oauth.Profile

@Database(entities = arrayOf(Profile::class), version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SolidarityDatabase : RoomDatabase() {
    companion object {
        private lateinit var INSTANCE: SolidarityDatabase
        fun getDatabase(context: Context): SolidarityDatabase? {
            synchronized(SolidarityDatabase::class.java) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        SolidarityDatabase::class.java, "solidarity_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()

            }
            return INSTANCE
        }
    }

    abstract fun profileDao(): ProfileDao

}
