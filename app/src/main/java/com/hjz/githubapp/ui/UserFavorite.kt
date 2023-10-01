package com.hjz.githubapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjz.githubapp.R
import com.hjz.githubapp.data.database.room.DbModule
import com.hjz.githubapp.data.model.DetailUserViewModel
import com.hjz.githubapp.data.model.FavoriteViewModel
import com.hjz.githubapp.databinding.ActivityDetailUserBinding
import com.hjz.githubapp.databinding.ActivityUserFavoriteBinding
import com.hjz.githubapp.ui.adapter.UserAdapter
import com.hjz.githubapp.ui.adapter.UserFavoriteAdapter

class UserFavorite : AppCompatActivity() {
    private lateinit var binding: ActivityUserFavoriteBinding
    private lateinit var adapter : UserFavoriteAdapter

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(DbModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = UserFavoriteAdapter()

        binding.rvUserFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvUserFavorite.setHasFixedSize(true)
        binding.rvUserFavorite.adapter = adapter

        favoriteViewModel.getUserFavorite().observe(this){
            adapter.setListFavorit(it)
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getUserFavorite().observe(this){
            adapter.setListFavorit(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle interaksi tombol kembali di sini (biasanya kembali ke Activity sebelumnya)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}