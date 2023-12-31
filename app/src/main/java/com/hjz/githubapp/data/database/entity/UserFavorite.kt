package com.hjz.githubapp.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_favorite")
@Parcelize
data class UserFavorite(
    @PrimaryKey
    var id : Int,

    @ColumnInfo(name = "avatar_url")
    var avatar_url : String,

    @ColumnInfo(name = "login")
    var login : String
) : Parcelable
