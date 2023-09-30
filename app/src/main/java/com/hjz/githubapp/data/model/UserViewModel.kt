package com.hjz.githubapp.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hjz.githubapp.data.response.ItemsItem
import com.hjz.githubapp.data.response.UsersResponse
import com.hjz.githubapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel()  {
    private val _users = MutableLiveData<List<ItemsItem>>()
    val users : LiveData<List<ItemsItem>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getSearchUsers(query : String) {
        _isLoading.value = true
        ApiConfig.getApiService().getSearchUsers(query)
            .enqueue(object : Callback<UsersResponse>{
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _users.postValue(response.body()?.items)
                    } else {
                        Log.e("UserViewModel", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("UserViewModel", "onFailure: ${t.message}")
                }
            })
    }
}