package com.example.anime.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.anime.data.local.favourites.FavouriteDao
import com.example.anime.data.local.favourites.FavouriteEntity

@Database(entities = [FavouriteEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getFavouriteDao(): FavouriteDao

    companion object {
        private var INSTANCE: LocalDatabase? = null
        fun getWallpaperDatabase(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
