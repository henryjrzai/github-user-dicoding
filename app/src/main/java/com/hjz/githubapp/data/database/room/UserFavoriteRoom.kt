package com.hjz.githubapp.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hjz.githubapp.data.database.dao.UserFavoriteDao
import com.hjz.githubapp.data.database.entity.UserFavorite

@Database(entities = [UserFavorite::class], version = 1, exportSchema = false)
abstract class UserFavoriteRoom : RoomDatabase(){
    abstract fun userFavoriteDao() : UserFavoriteDao
}