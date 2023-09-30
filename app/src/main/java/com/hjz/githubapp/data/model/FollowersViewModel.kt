package com.hjz.githubapp.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hjz.githubapp.data.response.FollowersResponse
import com.hjz.githubapp.data.response.FollowersResponseItem
import com.hjz.githubapp.data.response.ItemsItem
import com.hjz.githubapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val _followers = MutableLiveData<ArrayList<FollowersResponseItem>>()
    val followers : LiveData<ArrayList<FollowersResponseItem>> = _followers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getFollowers(username : String) {
        _isLoading.value = true
        ApiConfig.getApiService().getFollowers(username)
            .enqueue(object : Callback<ArrayList<FollowersResponseItem>>{
                override fun onResponse(
                    call: Call<ArrayList<FollowersResponseItem>>,
                    response: Response<ArrayList<FollowersResponseItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        _followers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<FollowersResponseItem>>, t: Throwable) {
                    _isLoading.value = true
                    Log.d("Fuiler", t.message.toString())
                }

            })
    }
}