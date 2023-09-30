package com.hjz.githubapp.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hjz.githubapp.data.database.entity.UserFavorite
import com.hjz.githubapp.data.database.room.DbModule
import com.hjz.githubapp.data.response.DetailUserResponse
import com.hjz.githubapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel (private val db : DbModule) : ViewModel() {
    private val _user = MutableLiveData<DetailUserResponse>()
    val users : LiveData<DetailUserResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    val resultInsertFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()

    private var isFavorite = false
    fun setFavorite(user: UserFavorite) {
        viewModelScope.launch {
            user.let {
                if (isFavorite) {
                    db.userDao.delete(user)
                    resultDeleteFavorite.value = true
                } else {
                    db.userDao.insert(user)
                    resultInsertFavorite.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun findFavorite(id: Int, listenFavorite: () -> Unit) {
        viewModelScope.launch {
            val user = db.userDao.findById(id)
            if (user != null) {
                listenFavorite()
                //isFavorite = true
            }
        }
    }

    fun getDetailUser(username : String) {
        _isLoading.value = true
        ApiConfig.getApiService().getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        _user.postValue(response.body())
                    } else {
                        Log.d("DetailUserViewModel", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    class Factory(private val db: DbModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailUserViewModel(db) as T
    }
}