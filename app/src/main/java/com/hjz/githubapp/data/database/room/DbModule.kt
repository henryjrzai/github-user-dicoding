package com.hjz.githubapp.data.database.room

import android.content.Context
import androidx.room.Room

class DbModule (private val context: Context) {
    private val db = Room.databaseBuilder(context, UserFavoriteRoom::class.java, "userfavorite.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.userFavoriteDao()
}