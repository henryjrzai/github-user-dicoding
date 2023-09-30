package com.hjz.githubapp.data.retrofit

import com.hjz.githubapp.data.response.DetailUserResponse
import com.hjz.githubapp.data.response.FollowersResponse
import com.hjz.githubapp.data.response.FollowersResponseItem
import com.hjz.githubapp.data.response.ItemsItem
import com.hjz.githubapp.data.response.UsersResponse
import com.hjz.githubapp.ui.FollowersFragment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ) : Call<UsersResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username : String) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ) :Call<ArrayList<FollowersResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ) :Call<ArrayList<ItemsItem>>
}