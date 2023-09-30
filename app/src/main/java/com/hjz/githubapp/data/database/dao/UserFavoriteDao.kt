package com.hjz.githubapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hjz.githubapp.data.database.entity.UserFavorite

@Dao
interface UserFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserFavorite)

    @Query("SELECT * FROM user_favorite")
    fun loadAll(): LiveData<MutableList<UserFavorite>>

    @Query("SELECT * FROM user_favorite WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): UserFavorite

    @Delete
    fun delete(user: UserFavorite)
}