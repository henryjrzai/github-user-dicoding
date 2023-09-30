package com.hjz.githubapp.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hjz.githubapp.data.database.room.DbModule

class FavoriteViewModel(private val db: DbModule) :ViewModel() {

    fun getUserFavorite() = db.userDao.loadAll()

    class Factory(private val db: DbModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}