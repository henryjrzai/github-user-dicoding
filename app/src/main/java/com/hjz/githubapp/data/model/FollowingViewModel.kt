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

class FollowingViewModel : ViewModel()  {
    private val _users = MutableLiveData<ArrayList<ItemsItem>>()
    val users : LiveData<ArrayList<ItemsItem>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getFollowing(query : String) {
        _isLoading.value = true
        ApiConfig.getApiService().getFollowing(query)
            .enqueue(object : Callback<ArrayList<ItemsItem>>{
                override fun onResponse(
                    call: Call<ArrayList<ItemsItem>>,
                    response: Response<ArrayList<ItemsItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        _users.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                    _isLoading.value = true
                    Log.d("Fuiler", t.message.toString())
                }

            })
    }
}