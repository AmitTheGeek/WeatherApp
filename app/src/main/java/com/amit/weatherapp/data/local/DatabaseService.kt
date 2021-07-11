package com.amit.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amit.weatherapp.data.local.room.Weather
import com.amit.weatherapp.data.local.room.WeatherDao


@Database(entities = [Weather::class], version = 1)
abstract class DatabaseService : RoomDatabase() {

    companion object {

        const val DATABASE_NAME = "weatherapp"

        @Volatile
        private var instance: DatabaseService? = null

        @JvmStatic
        fun getInstance(context: Context): DatabaseService = synchronized(this) {
            if (instance == null) instance = buildDatabase(context)
            return instance as DatabaseService
        }

        private fun buildDatabase(context: Context): DatabaseService = Room.databaseBuilder(
            context,
            DatabaseService::class.java,
            DATABASE_NAME
        ).build()
    }

    abstract fun weatherDao(): WeatherDao
}
